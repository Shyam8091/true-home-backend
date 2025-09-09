package com.example.true_home.service.impl;

import com.example.true_home.dto.AccountListingResponse;
import com.example.true_home.dto.ListingDto;
import com.example.true_home.dto.ListingResponseDto;
import com.example.true_home.dto.WishlistOrOrderOrAccountListingResponse;
import com.example.true_home.entity.Image;
import com.example.true_home.entity.Listing;
import com.example.true_home.entity.User;
import com.example.true_home.exception.TrueHomException;
import com.example.true_home.projections.ListingWithWishlistCountProjection;
import com.example.true_home.repository.ListingRepository;
import com.example.true_home.repository.UserRepo;
import com.example.true_home.security.AESUtil;
import com.example.true_home.service.ListingService;
import com.example.true_home.util.RestResponse;
import com.example.true_home.util.RestUtils;
import com.example.true_home.util.TrueHomeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class ListingServiceImpl implements ListingService {

    @Autowired
    private ListingRepository listingRepository;

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private GitHubUploadService gitHubUploadService;

    @Autowired
    private TrueHomeUtil trueHomeUtil;

    @Override
    public ResponseEntity<RestResponse<Page<ListingResponseDto>>> getAllListings(final boolean isLoggedIn, final String type, final int page,
                                                                                 final int size, final String city, List<String> locality) {
        long startTime = System.currentTimeMillis();
        Page<ListingWithWishlistCountProjection> projections;
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        if (isLoggedIn) {
            int accountId = trueHomeUtil.getUserIdFromAuthentication();
            projections = listingRepository.findAllListingsWithWishlistCount(accountId, type, city, locality, pageable);
        } else {
            projections = listingRepository.findAllListingsWithWishlistCount(type, city, locality, pageable);
        }

        long endTime = System.currentTimeMillis();
        double timeTakenInSeconds = (endTime - startTime) / 1000.0;
        System.out.println("Database call took: " + timeTakenInSeconds + " seconds");
        long startTimeProcess = System.currentTimeMillis();
        List<Listing> listings = projections.getContent().stream().map(projection -> {
            Listing listing = projection.getListing();
            listing.setWishlistCount(projection.getWishlistCount());
            listing.setWishListed(projection.isWishlisted());
            listing.setWishListId(projection.getWishlistedId());
            return listing;
        }).collect(Collectors.toList());
        List<ListingResponseDto> collect = getListingResponse(listings);
        long endTimeProcess = System.currentTimeMillis();
        double timeTakenInSecondsProcess = (endTimeProcess - startTimeProcess) / 1000.0;
        Page<ListingResponseDto> dtoPage = new PageImpl<>(
                collect,
                pageable,
                projections.getTotalElements()
        );
        System.out.println("Process: " + timeTakenInSecondsProcess + " seconds");
        return RestUtils.successResponse(dtoPage, HttpStatus.OK, "Listing fetched successfully");
    }

    private List<ListingResponseDto> getListingResponse(List<Listing> listings) {
        return listings.stream().map(listing -> {
            List<String> base64Images = listing.getImages().stream()
                    .map(Image::getImageUrl)
                    .collect(Collectors.toList());

            ListingResponseDto dto = new ListingResponseDto();
            dto.setId(listing.getId());
            dto.setProjectName(listing.getProjectName());
            dto.setType(listing.getType());
            dto.setCity(listing.getCity());
            dto.setAmenities(listing.getAmenities());
            dto.setBhk(listing.getBhk());
            dto.setFloor(listing.getFloor());
            dto.setLocality(listing.getLocality());
            dto.setTotalFloor(listing.getTotalFloor());
            dto.setPrice(listing.getPrice());
            dto.setDescription(listing.getDescription());
            dto.setPostal(listing.getPostal());
            dto.setArea(listing.getArea());
            dto.setApartmentType(listing.getApartmentType());
            try {
                dto.setOwnerId(AESUtil.encrypt(String.valueOf(listing.getOwner().getId())));
            } catch (Exception e) {
                throw new TrueHomException(null, "INVALID_ID", "OS_500");
            }
            dto.setImages(base64Images);
            dto.setWishlistCount(listing.getWishlistCount());
            dto.setWishListed(listing.isWishListed());
            dto.setWishListId(listing.getWishListId());
            dto.setVideoId(listing.getVideoId());
            dto.setPostingDate(getTimeAgo(listing.getCreatedAt()));
            dto.setCreateDate(listing.getCreatedAt().getTime());
            return dto;
        }).collect(Collectors.toList());
    }


    @Override
    @Transactional
    public ResponseEntity<RestResponse<Listing>> uploadListing(ListingDto listingDto) {
        String youtubeUrl;
        AtomicInteger counter = new AtomicInteger(1);
        User owner = userRepo.findById(trueHomeUtil.getUserIdFromAuthentication())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Listing listing = new Listing();

        listing.setProjectName(listingDto.getProjectName());
        listing.setType(listingDto.getType());
        listing.setBhk(listingDto.getBhk());
        listing.setFloor(listingDto.getFloor());
        listing.setTotalFloor(listingDto.getTotalFloor());
        listing.setPrice(listingDto.getPrice());
        listing.setDescription(listingDto.getDescription());
        listing.setAmenities(listingDto.getAmenities());
        listing.setCity(listingDto.getCity());
        listing.setPostal(listingDto.getPostal());
        listing.setState(listingDto.getState());
        listing.setArea(listingDto.getArea());
        listing.setLocality(listing.getLocality());
        listing.setApartmentType(listingDto.getApartmentType());

        List<Image> imageEntities = listingDto.getImages().stream()
                .map(base64Image -> {
                    int currentIndex = counter.getAndIncrement();
                    Image image = new Image();
                    String imagePath = "images/" + listingDto.getProjectName() + "/" + listingDto.getProjectName() + "_" + owner.getId() + "_" + currentIndex + "" + ".png";
                    String shyam8091 = gitHubUploadService.uploadImageToGitHub(
                            base64Image, "Shyam8091", "true-images", imagePath
                    );
                    image.setImageUrl(imagePath);
                    image.setListing(listing);  // Associate the image with this listing
                    return image;
                }).collect(Collectors.toList());

        listing.setImages(imageEntities);

        File videoFile = convertMultipartFileToFile(listingDto.getFile());
        String customDescription = listingDto.getProjectName() + " " + listingDto.getBhk() + " BHK";
        try {
            youtubeUrl = YouTubeUploader.uploadVideo(videoFile, listingDto.getProjectName(), customDescription);
        } catch (Exception exception) {
            youtubeUrl = "";
        }
        listing.setVideoId(youtubeUrl);
        listing.setOwner(owner);
        Listing save = listingRepository.save(listing);
        Listing l = new Listing();
        l.setId(save.getId());
        if (save.getId() != null) {
            return RestUtils.successResponse(l, HttpStatus.OK, "Listing uploaded successfully");
        } else {
            throw new RuntimeException("Failed to save listing");
        }

    }

    @Override
    public ResponseEntity<RestResponse<AccountListingResponse>> getListingFromAccount() {
        List<Listing> listingFromAccount = listingRepository.getListingFromAccount(trueHomeUtil.getUserIdFromAuthentication());
        List<WishlistOrOrderOrAccountListingResponse> listingResponse = getAccountListingResponse(listingFromAccount);
        return RestUtils.successResponse(AccountListingResponse.builder().listingsFound(!listingResponse.isEmpty()).accountListings(listingResponse).build(), HttpStatus.OK, "Listing fetched successfully");
    }

    //ADD projection empty check
    @Override
    @Transactional
    public ResponseEntity<RestResponse<ListingResponseDto>> getProductById(Integer id, final boolean isLoggedIn) {
        ListingWithWishlistCountProjection projection;
        if (isLoggedIn) {
            int accountId = trueHomeUtil.getUserIdFromAuthentication();
            //TODO - when loggedInUser we are getting wishListId for all product it should only come for logedIn user id only
            projection = listingRepository.findListingWithWishlistInfo(id, accountId);
        } else {
            projection = listingRepository.findListingWithWishlistInfo(id);
        }
        Listing listing = projection.getListing();
        ListingResponseDto responseDto = ListingResponseDto.builder().
                id(listing.getId()).
                projectName(listing.getProjectName()).
                type(listing.getType()).
                city(listing.getCity()).
                bhk(listing.getBhk()).
                price(listing.getPrice()).
                amenities(listing.getAmenities()).
                locality(listing.getLocality()).
                postal(listing.getPostal()).
                floor(listing.getFloor()).
                totalFloor(listing.getTotalFloor()).
                images(listing.getImages().stream()
                        .map(Image::getImageUrl)
                        .collect(Collectors.toList())).
                area(listing.getArea()).
                apartmentType(listing.getApartmentType()).
                wishlistCount(projection.getWishlistCount()).
                wishListed(projection.isWishlisted()).
                wishListId(projection.getWishlistedId()).build();

        return RestUtils.successResponse(responseDto, HttpStatus.OK, "Listing fetched successfully");
    }

    @Override
    public ResponseEntity<RestResponse<List<ListingResponseDto>>> getHomePageListings(boolean isLoggedIn) {
        Pageable pageable = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "createdAt"));
        Integer accountId = null;
        if (isLoggedIn) {
            accountId = trueHomeUtil.getUserIdFromAuthentication();
        }
        List<ListingWithWishlistCountProjection> rentListing = listingRepository.findTopListingsByType("Rent", accountId, pageable);
        List<ListingWithWishlistCountProjection> sellListing = listingRepository.findTopListingsByType("Sell", accountId, pageable);
        rentListing.addAll(sellListing);
        List<Listing> listings = rentListing.stream().map(projection -> {
            Listing listing = projection.getListing();
            listing.setWishlistCount(projection.getWishlistCount());
            listing.setWishListed(projection.isWishlisted());
            listing.setWishListId(projection.getWishlistedId());
            return listing;
        }).collect(Collectors.toList());
        List<ListingResponseDto> collect = getListingResponse(listings);
        return RestUtils.successResponse(collect, HttpStatus.OK, "Home page Listing fetched successfully");
    }


    private List<WishlistOrOrderOrAccountListingResponse> getAccountListingResponse(List<Listing> listings) {
        return listings.stream().map(listing -> {

            WishlistOrOrderOrAccountListingResponse dto = new WishlistOrOrderOrAccountListingResponse();
            dto.setId(listing.getId());
            dto.setProduct(listing);
            return dto;
        }).collect(Collectors.toList());
    }

    private File convertMultipartFileToFile(MultipartFile multipartFile) {
        try {
            File file = File.createTempFile("youtube_upload_", "_" + multipartFile.getOriginalFilename());
            try (FileOutputStream fos = new FileOutputStream(file)) {
                fos.write(multipartFile.getBytes());
            }
            return file;
        } catch (Exception exception) {
            return null;
        }
    }

    public static String getTimeAgo(Timestamp timestamp) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime dateTime = timestamp.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();

        long days = ChronoUnit.DAYS.between(dateTime, now);
        long weeks = ChronoUnit.WEEKS.between(dateTime, now);
        long months = ChronoUnit.MONTHS.between(dateTime, now);
        long years = ChronoUnit.YEARS.between(dateTime, now);

        if (days < 7) {
            return days == 0 ? "Today" : days + " day" + (days > 1 ? "s" : "") + " ago";
        } else if (months < 1) {
            return weeks + " week" + (weeks > 1 ? "s" : "") + " ago";
        } else if (years < 1) {
            return months + " month" + (months > 1 ? "s" : "") + " ago";
        } else {
            return years + " year" + (years > 1 ? "s" : "") + " ago";
        }
    }

}


