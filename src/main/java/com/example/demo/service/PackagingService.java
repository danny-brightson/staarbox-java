package com.example.demo.service;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.FinalPackDto;
import com.example.demo.dto.PackagingDto;
import com.example.demo.dto.PlanCountDto;
import com.example.demo.dto.SimpleIngredient;
import com.example.demo.entity.CustomerDetails;
import com.example.demo.entity.DeliveryPersonDetails;
import com.example.demo.entity.LkpAvailableDistrict;
import com.example.demo.entity.LkpDeliveryTimings;
import com.example.demo.entity.LkpFruitAndNuts;
import com.example.demo.entity.LkpPackDetails;
import com.example.demo.entity.Packaging;
import com.example.demo.entity.PackerItemStatus;
import com.example.demo.entity.TodaysDeliveryDetails;
import com.example.demo.projection.QrCodeProjection;
import com.example.demo.repo.CommonandPragnentpackDetailsRepo;
import com.example.demo.repo.CustomerDetailsRepo;
import com.example.demo.repo.CustomizedpackagedetailsRepo;
import com.example.demo.repo.DeliveryPersonDetailsRepo;
import com.example.demo.repo.LkpAvailableDistrictRepo;
import com.example.demo.repo.LkpDeliveryTimingsRepo;
import com.example.demo.repo.LkpFruitAndNutsRepo;
import com.example.demo.repo.LkpPackDetailsRepo;
import com.example.demo.repo.PackageTypeRepo;
import com.example.demo.repo.PackagingRepo;
import com.example.demo.repo.PackerItemStatusRepo;
import com.example.demo.repo.TodaysDeliveryDetailsRepo;
import com.example.demo.util.QRCodeGenerator;

@Service
public class PackagingService {

	@Autowired
	private CustomerDetailsRepo customerRepo;
	@Autowired
	private LkpPackDetailsRepo lkpPackDetailsRepo;
	@Autowired
	private LkpAvailableDistrictRepo lkpAvailableDistrictRepo;
	@Autowired
	private PackagingRepo packagingRepo;

	@Autowired
	private PackageTypeRepo PackageTypeRepo;
	@Autowired
	private CustomizedpackagedetailsRepo customizedpackagedetailsRepo;
	
	@Autowired
	private com.example.demo.repo.AllergicPackageDetailsRepo AllergicPackageDetailsRepo;

	@Autowired
	private CommonandPragnentpackDetailsRepo commonandPragnentpackDetailsRepo;
	
	@Autowired
	private LkpFruitAndNutsRepo lkpFruitAndNutsRepo;
	
	@Autowired
	private PackerItemStatusRepo packerItemStatusRepo;
	
	@Autowired
	private DeliveryPersonDetailsRepo deliveryPersonDetailsRepo;
	
	@Autowired
	private LkpDeliveryTimingsRepo lkpDeliveryTimingsRepo;
	
	@Autowired
	private TodaysDeliveryDetailsRepo todaysDeliveryDetailsRepo;
	
	public List<PackagingDto> mapToPackagingDtos(List<Object[]> rawData) {
		Map<String, Integer> map = new HashMap<>();
		int i = 0;
		map.put("id", i++);
		map.put("zoneId", i++);
		map.put("distanceId", i++);
		map.put("districtId", i++);
		map.put("deliveryCode", i++);
		map.put("packDetailsId", i++);
		map.put("deliveryTimingId", i++);
		map.put("name", i++);
		map.put("isPragnent", i++);
		
		

		return rawData.stream()
		        .map(row -> new PackagingDto(
		            ((Number) row[map.get("id")]).longValue(),              // ID
		            ((Number) row[map.get("districtId")]).intValue(),       // District ID
		            ((Number) row[map.get("zoneId")]).intValue(),           // Zone ID
		            ((Number) row[map.get("distanceId")]).intValue(),       // Distance ID
		            ((Number) row[map.get("deliveryTimingId")]).intValue(), // Delivery Timing ID
		            ((Number) row[map.get("packDetailsId")]).intValue(),    // Pack Details ID
		            (String) row[map.get("deliveryCode")],
		            (String) row[map.get("name")],
		            (Boolean) row[map.get("isPragnent")]// Delivery Code
		        ))
		        .collect(Collectors.toList());
	}

	public List<PackagingDto> getSortedPackagesByDistrict(int districtId) {
		List<Object[]> rawData = customerRepo.findAllByDistrictIdOrdered(districtId);
		System.out.println("Customer count for district " + districtId + ": " + rawData.size());
		return mapToPackagingDtos(rawData);
	}

	public void generateCodesForPacking(int districtId) {
		// Delete only previous packaging for the given district, not the whole table
		packagingRepo.deleteByDistrictId(districtId);

		List<PackagingDto> customers = getSortedPackagesByDistrict(districtId);
		Map<Integer, Integer> districtBoxCounter = new HashMap<>();

		for (PackagingDto customer : customers) {
			try {
				Optional<LkpPackDetails> packageName = lkpPackDetailsRepo.findById((long) customer.getPackDetailsId());
				Optional<LkpAvailableDistrict> districtName = lkpAvailableDistrictRepo
						.findById((long) customer.getDistrictId());

				String planCode = packageName.get().getPlanCode();
				
				if(customer .isPragnent()) {
					 planCode = "P"+ packageName.get().getPlanCode();
				}
				int currentDistrictId = customer.getDistrictId();
				int boxNumber = districtBoxCounter.getOrDefault(currentDistrictId, 0) + 1;
				districtBoxCounter.put(currentDistrictId, boxNumber);

				Packaging entity = new Packaging();
				entity.setCustomerId(customer.getId());
				entity.setCustomerName(customer.getName());
				entity.setDistrictId(currentDistrictId);
				entity.setBoxNumber((long) boxNumber);
				entity.setStatusId(1L);
				entity.setCreatedBy("User");
				entity.setCreatedTime(LocalDateTime.now());
				entity.setPlanCode(planCode);
				entity = packagingRepo.save(entity);

				String numberCode = entity.getBoxNumber()  + planCode +  "-"
						+ districtName.get().getDistrictCode() + "-" + customer.getDeliveryCode();
				byte[] qrImage = QRCodeGenerator.generateQRCode(String.valueOf(entity.getId()), 250, 250);

				entity.setQrCode(qrImage);
				entity.setNumberCode(numberCode);

				packagingRepo.save(entity);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public boolean isCommon(boolean isPregnant, boolean isAllergic, boolean isCustomized) {
		return !isPregnant && !isAllergic && !isCustomized;
	}
	public boolean doesCustomizedPackageExist(Long customerId, Long weekdayId, LocalDate date) {
	    Long count = customizedpackagedetailsRepo.countCustomizedEntries(customerId, weekdayId, date);
	    return count != null && count > 0;
	}

	
	
	public List<FinalPackDto> getPackageBoxlist(int districtId, long boxnumber) {
		 Packaging data = packagingRepo.findByDistrictIdandBoxNumber(districtId,boxnumber);
		 if (data == null) {
			 throw new RuntimeException("Box number does not exist for district: " + districtId);
		 }
		
		
		CustomerDetails customer = customerRepo.findById(data.getCustomerId())
				.orElseThrow(() -> new RuntimeException("Customer not found"));
		System.out.println(data.getCustomerId());
		LocalDate businessDate = LocalDate.now();
		DayOfWeek day = businessDate.getDayOfWeek();
		int weekday=0;
	    if (day.getValue() == 7) {
	    	 weekday=1;
	    }
	    else {
	    	weekday = day.getValue() + 1;
	    }
		System.out.println(businessDate);
		System.out.println(weekday);
		boolean isCustomizedToday = doesCustomizedPackageExist(data.getCustomerId(),
				Long.valueOf(weekday), businessDate);

		boolean isCommon = isCommon(customer.isPragnent(), customer.isAlergic(), customer.isCustomized());

		 Integer packageTypeId = null;

		    // Only fetch packageTypeId if it's not customized
		    if (Boolean.FALSE.equals(isCustomizedToday)) {
		    	Integer optionalTypeId = PackageTypeRepo.getPackageTypeId(
		                customer.isPragnent(),
		                customer.isAlergic(),
		                customer.isCustomized(),
		                isCommon,
		                customer.getPackDetailsId()
		        ).orElse(0);
		        
		        packageTypeId = optionalTypeId;
		        
		    }
		    Boolean eggPreferdToSend = customer.isEggPreferd();

		 // force egg = 0 for packageTypeId 12 or 13
		 if (packageTypeId != null && (packageTypeId == 12 || packageTypeId == 13)) {
		     eggPreferdToSend = false;
		 }
		 System.out.println("PackageTypeId = " + packageTypeId);
		 System.out.println("EggPreferd Sent = " + eggPreferdToSend);


		List<Object[]> response = null;
		if (isCustomizedToday) {
			response = customizedpackagedetailsRepo.getPackStatusForCustomer(data.getCustomerId(), weekday, businessDate);
		} else if (Boolean.TRUE.equals(customer.isAlergic())) {
			response = AllergicPackageDetailsRepo.getPackStatusForCustomer(data.getCustomerId(), weekday);
		} else if (Boolean.TRUE.equals(customer.isPragnent())) {
			response = commonandPragnentpackDetailsRepo.getPackStatusForCustomer(data.getCustomerId(),packageTypeId, weekday);
		}

		else {
			response = commonandPragnentpackDetailsRepo.getPackStatusForCustomer(data.getCustomerId(),packageTypeId, weekday);
		};
		System.out.println(response.getClass());

		List<FinalPackDto> responseList = new ArrayList<>();

		Map<String, Integer> indexMap = getIndexMap();

		for (Object[] row : response) {
			FinalPackDto dto = new FinalPackDto();

			dto.setEggAdded((Boolean) row[indexMap.get("isEggAdded")]);

			dto.setEggOrSeed(new SimpleIngredient(
				    (String) row[indexMap.get("eggOrSeed")],
				    (String) row[indexMap.get("eggOrSeedWeight")],
				    toBoolean(row[indexMap.get("eggPacked")])
				));


			// Fruits
			List<SimpleIngredient> fruits = new ArrayList<>();
			for (int i = 1; i <= 6; i++) {
				String name = (String) row[indexMap.getOrDefault("fruit" + i + "Name", -1)];
				if (name != null) {
					fruits.add(new SimpleIngredient(
						    name,
						    (String) row[indexMap.get("fruit" + i + "Weight")],
						    toBoolean(row[indexMap.get("fruit" + i + "Packed")])
						));
				}
			}
			dto.setFruits(fruits);

			// Nuts
			List<SimpleIngredient> nuts = new ArrayList<>();
			for (int i = 1; i <= 5; i++) {
				String name = (String) row[indexMap.getOrDefault("nut" + i + "Name", -1)];
				if (name != null) {
					nuts.add(new SimpleIngredient(
						    name,
						    (String) row[indexMap.get("nut" + i + "Weight")],
						    toBoolean(row[indexMap.get("nut" + i + "Packed")])));
				}
			}
			// ==========================
			// Sandwich (single item)
			// ==========================
			String sandwichName = (String) row[indexMap.get("sandwichName")];

			if (sandwichName != null) {
			    SimpleIngredient sandwich = new SimpleIngredient(
			        sandwichName,
			        null, // ❌ no weight
			        toBoolean(row[indexMap.get("sandwichPacked")])
			    );
			    dto.setSandwich(sandwich);
			}

			dto.setNuts(nuts);
			dto.setBoxNumber((int) boxnumber);
			dto.setNumberCode(data.getNumberCode());
			dto.setVerified(data.getIsVerified());
			System.out.println("dto: " +dto);
			responseList.add(dto);
		}
		System.out.println("responseList " +responseList);
		return responseList;
	
	}

	private Map<String, Integer> getIndexMap() {
	    Map<String, Integer> map = new HashMap<>();
	    int i = 0;

	    map.put("isEggAdded", i++);
	    map.put("eggOrSeed", i++);
	    map.put("eggOrSeedWeight", i++);
	    map.put("eggPacked", i++);
	   
       // Corrected spelling

	    for (int j = 1; j <= 6; j++) {
	        map.put("fruit" + j + "Name", i++);
	        map.put("fruit" + j + "Weight", i++);
	        map.put("fruit" + j + "Packed", i++);
	    }
	    for (int j = 1; j <= 5; j++) {
	        map.put("nut" + j + "Name", i++);
	        map.put("nut" + j + "Weight", i++);
	        map.put("nut" + j + "Packed", i++);
	    }
	    
	    // ⭐ Sandwich (single — NO weight)
	    map.put("sandwichName", i++);
	    map.put("sandwichPacked", i++);
	    
	    map.put("boxNumber", i++);
	    map.put("numberCode", i++);
	    return map;
	}
	
	private Boolean toBoolean(Object obj) {
	    if (obj instanceof Boolean) return (Boolean) obj;
	    if (obj instanceof Number) return ((Number) obj).intValue() != 0;
	    return false;
	}

	public int getFruitOrNutId(String fruitAndNuts) {
	    return lkpFruitAndNutsRepo.findByFruitAndNutsIgnoreCase(fruitAndNuts)
	           .map(LkpFruitAndNuts::getId)
	           .orElseThrow(() -> new RuntimeException("Fruit/Nut not found: " + fruitAndNuts));
	}
	public void updatePackedStatusByNames(Long boxNumber, Long packerId, List<String> productNames, Integer districtId) {
	    for (String name : productNames) {
	        int productId = getFruitOrNutId(name);
	        
	   	 Packaging data = packagingRepo.findByDistrictIdandBoxNumber(districtId,boxNumber);
	   	 System.out.println(data.getNumberCode());

	        Optional<PackerItemStatus> optional = packerItemStatusRepo
	            .findByBoxNumberAndDistrictIdAndProductId(boxNumber, districtId, (long) productId);
	        
	       

	        PackerItemStatus entity = new PackerItemStatus();
	        if (optional.isPresent()) {
	        	 System.out.println(optional.get().getId());
	            entity = optional.get();
	            entity.setPacked(true);
	            entity.setPackedTime(LocalDateTime.now());
	        } else {
	            
	            entity.setBoxNumber(boxNumber);
	            entity.setCustomerId(data.getCustomerId());
	            entity.setDistrictId(districtId);
	            entity.setPackerId(packerId);
	            entity.setProductId((long) productId);
	            entity.setPacked(true);
	            entity.setPackedTime(LocalDateTime.now());
	        }

	        packerItemStatusRepo.save(entity);
	    }
	}

	public List<PlanCountDto> getNumberOfBox(int districtId) {
		List<Object[]> rawResult = packagingRepo.getPlanCountsWithTotal(districtId);
		List<PlanCountDto> result = rawResult.stream()
			    .map(row -> new PlanCountDto(
			        (String) row[0],
			        ((Number) row[1]).longValue(),
			        ((Number) row[2]).longValue()
			    ))
			    .collect(Collectors.toList());

		return result;
	}

	public String getDeliveryPersonDetails(String phoneNumber) {
		Optional<DeliveryPersonDetails> deliveryperson  =deliveryPersonDetailsRepo.findByPhoneNumber(phoneNumber);
		
		Optional<LkpAvailableDistrict> districtName = lkpAvailableDistrictRepo
				.findById((long) deliveryperson.get().getDistrictId());
		String numberCode = districtName.get().getDistrictCode() +"-"+ deliveryperson.get().getDeliveryCode();
		return numberCode;
	}

	public String PackVerification(int districtId, long boxnumber) {
		Packaging packaging  = packagingRepo.findByDistrictIdandBoxNumber(districtId, boxnumber);
		 
		
		if (packaging.getIsVerified() == true) {
			 return ("It is already verified");
		 }
		 else {
			 packagingRepo.updateVerification(packaging.getId());
			 CustomerDetails customer = customerRepo.findById(packaging.getCustomerId())
						.orElseThrow(() -> new RuntimeException("Customer not found"));
			 Optional<LkpDeliveryTimings> delivery = lkpDeliveryTimingsRepo.findById((long) customer.getDelivaryTimingId());
			 TodaysDeliveryDetails todaysDeliveryDetails = new TodaysDeliveryDetails();
			 todaysDeliveryDetails.setNumberCode(packaging.getNumberCode());
			 todaysDeliveryDetails.setBoxNumber(packaging.getBoxNumber());
			 todaysDeliveryDetails.setCustomerId(customer.getId());
			 todaysDeliveryDetails.setCustomerName(customer.getName());
			 todaysDeliveryDetails.setAddress(customer.getAddressLine1()+customer.getAddressLine2());
			 todaysDeliveryDetails.setDistrictId(packaging.getDistrictId());
			 todaysDeliveryDetails.setDeliveryTiming(delivery.get().getDeliveryTiming());
			 todaysDeliveryDetails.setCustomerLatitude(customer.getLatitude());
			 todaysDeliveryDetails.setCustomerLongitude(customer.getLongitude());
			 todaysDeliveryDetails.setStatusId(1L);
			 todaysDeliveryDetails.setCreatedBy("User");
			 todaysDeliveryDetails.setCreatedTime(LocalDateTime.now());
			 todaysDeliveryDetailsRepo.save(todaysDeliveryDetails);
			 
			 return ("package is verified");
		 }
	
		
	}

//	public List<String> createImage(List<QrCodeProjection> qrDataList) {
//	    List<String> imageList = new ArrayList<>();
//
//	    for (QrCodeProjection data : qrDataList) {
//	        byte[] qrBytes = data.getQrCode(); // ✅ Directly get bytes
//
//	        BufferedImage qrImage = null;
//	        try {
//	            qrImage = ImageIO.read(new java.io.ByteArrayInputStream(qrBytes));
//	        } catch (IOException e) {
//	            e.printStackTrace();
//	            continue; // Skip this record if invalid
//	        }
//
//	        int width = 300;
//	        int height = 400;
//	        BufferedImage combined = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
//	        Graphics2D g = combined.createGraphics();
//
//	        // Background
//	        g.setColor(Color.WHITE);
//	        g.fillRect(0, 0, width, height);
//
//	        // Name
//	        g.setColor(Color.BLACK);
//	        g.setFont(new Font("Arial", Font.BOLD, 24));
//	        FontMetrics fm = g.getFontMetrics();
//	        int nameX = (width - fm.stringWidth(data.getCustomerName())) / 2;
//	        g.drawString(data.getCustomerName(), nameX, 40);
//
//	        // QR
//	        g.drawImage(qrImage, 50, 60, 200, 200, null);
//
//	        // Number code
//	        g.setFont(new Font("Arial", Font.PLAIN, 20));
//	        fm = g.getFontMetrics();
//	        int codeX = (width - fm.stringWidth(data.getNumberCode())) / 2;
//	        g.drawString(data.getNumberCode(), codeX, 300);
//
//	        g.dispose();
//
//	        // Convert to Base64
//	        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//	        try {
//	            ImageIO.write(combined, "png", baos);
//	        } catch (IOException e) {
//	            e.printStackTrace();
//	            continue;
//	        }
//	        String base64Image = "data:image/png;base64," + Base64.getEncoder().encodeToString(baos.toByteArray());
//
//	        imageList.add(base64Image);
//	    }
//	    return imageList;
//	}
	
public List<String> createImage(List<QrCodeProjection> qrDataList) {

    List<String> imageList = new ArrayList<>();

    int DPI = 203;

    int qrSize = 180;          // fixed QR size
    int SIDE_PADDING = 5;     // 👈 clear left & right padding
    int TOP_PADDING = 10;
    int BOTTOM_PADDING = 10;

    int WIDTH = qrSize + (SIDE_PADDING * 2);

    for (QrCodeProjection data : qrDataList) {
        try {
            BufferedImage qrImage =
                    ImageIO.read(new ByteArrayInputStream(data.getQrCode()));

            // Fonts
            int nameFontPx = (int) Math.round((8.0 * DPI) / 72.0);
            int codeFontPx = (int) Math.round((7.0 * DPI) / 72.0);

            Font nameFont = new Font("Arial", Font.BOLD, nameFontPx);
            Font codeFont = new Font("Arial", Font.PLAIN, codeFontPx);

            Graphics2D tempG =
                    new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB).createGraphics();

            FontMetrics nameFM = tempG.getFontMetrics(nameFont);
            FontMetrics codeFM = tempG.getFontMetrics(codeFont);
            tempG.dispose();

            // Exact height (no extra space)
            int HEIGHT =
                    TOP_PADDING +
                    nameFM.getHeight() +
                    6 +
                    qrSize +
                    6 +
                    codeFM.getHeight() +
                    BOTTOM_PADDING;

            BufferedImage combined =
                    new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);

            Graphics2D g = combined.createGraphics();
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

            g.setColor(Color.WHITE);
            g.fillRect(0, 0, WIDTH, HEIGHT);
            g.setColor(Color.BLACK);

            int y = TOP_PADDING;

            // NAME
            g.setFont(nameFont);
            y += nameFM.getAscent();
            int nameX = (WIDTH - nameFM.stringWidth(data.getCustomerName())) / 2;
            g.drawString(data.getCustomerName(), nameX, y);
            y += nameFM.getDescent() + 6;

            // QR
            int qrX = SIDE_PADDING; // 👈 explicit left padding
            g.drawImage(qrImage, qrX, y, qrSize, qrSize, null);
            y += qrSize + 6;

            // CODE
            g.setFont(codeFont);
            y += codeFM.getAscent();
            int codeX = (WIDTH - codeFM.stringWidth(data.getNumberCode())) / 2;
            g.drawString(data.getNumberCode(), codeX, y);

            g.dispose();

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(combined, "png", baos);

            imageList.add(
                "data:image/png;base64," +
                Base64.getEncoder().encodeToString(baos.toByteArray())
            );

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    return imageList;
}











}
