package com.example.demo.service;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.CustomizedPackageRequest;
import com.example.demo.dto.IngredientDto;
import com.example.demo.dto.OptionalIngredientDto;
import com.example.demo.dto.PackageResponseDto;
import com.example.demo.entity.CustomerDetails;
import com.example.demo.entity.CustomizedPackageDetails;
import com.example.demo.entity.LkpFruitAndNuts;
import com.example.demo.repo.CommonandPragnentpackDetailsRepo;
import com.example.demo.repo.CustomerDetailsRepo;
import com.example.demo.repo.CustomizedpackagedetailsRepo;
import com.example.demo.repo.LkpFruitAndNutsRepo;
import com.example.demo.repo.PackageTypeRepo;

@Service
public class CustomaizationService {
	// @Autowired
	// private CustomerDetailsRepo customerRepo;

	@Autowired
	private com.example.demo.repo.AllergicPackageDetailsRepo AllergicPackageDetailsRepo;

	@Autowired
	private CommonandPragnentpackDetailsRepo commonandPragnentpackDetailsRepo;

	@Autowired
	private CustomizedpackagedetailsRepo customizedpackagedetailsRepo;

	@Autowired
	private PackageTypeRepo PackageTypeRepo;

	@Autowired
	private LkpFruitAndNutsRepo lkpFruitAndNutsRepo;

	@Autowired
	private CustomerDetailsRepo customerDetailsRepo;

	public boolean isCommon(boolean isPregnant, boolean isAllergic, boolean isCustomized) {
		return !isPregnant && !isAllergic && !isCustomized;
	}

	public boolean doesCustomizedPackageExist(Long customerId, Long weekdayId, LocalDate date) {
		Long count = customizedpackagedetailsRepo.countCustomizedEntries(customerId, weekdayId, date);
		return count != null && count > 0;
	}

//	public List<PackageResponseDto> getPackageByCustomerId(long customerId) {
//		CustomerDetails customer = customerRepo.findById(customerId)
//				.orElseThrow(() -> new RuntimeException("Customer not found"));
//		LocalDate businessDate = LocalDate.now().plusDays(1);
//		DayOfWeek day = businessDate.getDayOfWeek();
//		int weekday = day.getValue() + 1;
//		Boolean isCustomizedToday = doesCustomizedPackageExist(customerId, Long.valueOf(weekday), businessDate);
//		int packageTypeId = 0;
//		Boolean isCommon = isCommon(customer.isPragnent(), customer.isAlergic(), customer.isCustomized());
//		if (Boolean.FALSE.equals(isCustomizedToday)) {
//			 packageTypeId = PackageTypeRepo.getPackageTypeId(customer.isPragnent(), customer.isAlergic(),
//					customer.isCustomized(), isCommon, customer.getPackDetailsId());
//		}
//
//		List<Object[]> response = null;
//
//		if (isCustomizedToday) {
//			response = customizedpackagedetailsRepo.findDetailedPack(customerId, weekday, businessDate);
//		} else if (Boolean.TRUE.equals(customer.isAlergic())) {
//			response = AllergicPackageDetailsRepo.findDetailedPack(customerId, weekday);
//		} else if (Boolean.TRUE.equals(customer.isPragnent())) {
//			response = commonandPragnentpackDetailsRepo.getPackageItemView(packageTypeId, weekday,
//					customer.isEggPreferd());
//		}
//
//		else {
//			response = commonandPragnentpackDetailsRepo.getPackageItemView(packageTypeId, day.getValue(),
//					customer.isEggPreferd());
//		}
//		;
//
//		List<PackageResponseDto> responseList = new ArrayList<>();
//
//		Map<String, Integer> indexMap = getIndexMap();
//
//		for (Object[] row : response) {
//			PackageResponseDto dto = new PackageResponseDto();
//
//			dto.setEggAdded((Boolean) row[indexMap.get("isEggAdded")]);
//
//			dto.setEggOrSeed(new IngredientDto((String) row[indexMap.get("eggOrSeed")],
//					(String) row[indexMap.get("eggOrSeedWeight")], toBigDecimal(row[indexMap.get("eggOrSeedProtein")]),
//					toBigDecimal(row[indexMap.get("eggOrSeedFiber")]), toBigDecimal(row[indexMap.get("eggOrSeedFat")]),
//					toBigDecimal(row[indexMap.get("eggOrSeedCarboHydreate")]),
//					toBigDecimal(row[indexMap.get("eggOrSeedSugar")])));
//
//			// Fruits
//			List<IngredientDto> fruits = new ArrayList<>();
//			for (int i = 1; i <= 5; i++) {
//				String name = (String) row[indexMap.getOrDefault("fruit" + i + "Name", -1)];
//				if (name != null) {
//					fruits.add(new IngredientDto(name, (String) row[indexMap.get("fruit" + i + "Weight")],
//							toBigDecimal(row[indexMap.get("fruit" + i + "Protein")]),
//							toBigDecimal(row[indexMap.get("fruit" + i + "Fiber")]),
//							toBigDecimal(row[indexMap.get("fruit" + i + "Fat")]),
//							toBigDecimal(row[indexMap.get("fruit" + i + "CarboHydreate")]),
//							toBigDecimal(row[indexMap.get("fruit" + i + "Sugar")])));
//
//				}
//			}
//			dto.setFruits(fruits);
//
//			// Nuts
//			List<IngredientDto> nuts = new ArrayList<>();
//			for (int i = 1; i <= 5; i++) {
//				String name = (String) row[indexMap.getOrDefault("nut" + i + "Name", -1)];
//				if (name != null) {
//					nuts.add(new IngredientDto(name, (String) row[indexMap.get("nut" + i + "Weight")],
//							toBigDecimal(row[indexMap.get("nut" + i + "Protein")]),
//							toBigDecimal(row[indexMap.get("nut" + i + "Fiber")]),
//							toBigDecimal(row[indexMap.get("nut" + i + "Fat")]),
//							toBigDecimal(row[indexMap.get("nut" + i + "CarboHydreate")]),
//							toBigDecimal(row[indexMap.get("nut" + i + "Sugar")])));
//
//				}
//			}
//			dto.setNuts(nuts);
//			// Optional
//			List<OptionalIngredientDto> optionals = new ArrayList<>();
//			for (int i = 1; i <= 6; i++) {
//				String name = (String) row[indexMap.getOrDefault("optional" + i + "Name", -1)];
//				if (name != null) {
//					optionals.add(new OptionalIngredientDto(name, (String) row[indexMap.get("optional" + i + "Weight")],
//							(Boolean) row[indexMap.get("Optional" + i + "IsFruit")],
//							toBigDecimal(row[indexMap.get("optional" + i + "Fiber")]),
//							toBigDecimal(row[indexMap.get("optional" + i + "Protein")]),
//							toBigDecimal(row[indexMap.get("optional" + i + "Fat")]),
//							toBigDecimal(row[indexMap.get("optional" + i + "CarboHydreate")]),
//							toBigDecimal(row[indexMap.get("optional" + i + "Sugar")])));
//
//				}
//			}
//			dto.setOptionals(optionals);
//
//			responseList.add(dto);
//		}
//		return responseList;
//	}

	public List<PackageResponseDto> getPackageByCustomerId(long customerId) {
		CustomerDetails customer = customerDetailsRepo.findById(customerId)
				.orElseThrow(() -> new RuntimeException("Customer not found"));

		LocalDate businessDate = LocalDate.now().plusDays(1);

		 //LocalDateTime businessDate = LocalDateTime.now().plusDays(1);
		 DayOfWeek day = LocalDate.now().getDayOfWeek();

		//DayOfWeek day = businessDate.getDayOfWeek();
		int weekday = 0;
		if (day.getValue() == 7) {
			weekday = 1;
		} else {
			weekday = day.getValue() + 1;
		}

		// Boolean isCustomizedToday = doesCustomizedPackageExist(customerId, Long.valueOf(weekday), businessDate);
		Boolean isCustomizedToday = doesCustomizedPackageExist(customerId, Long.valueOf(weekday), businessDate);

		System.out.println("==== DEBUG START ====");
		System.out.println("CustomerId: " + customerId);
		System.out.println("BusinessDate: " + businessDate);
		System.out.println("Weekday: " + weekday);
		System.out.println("isCustomizedToday: " + isCustomizedToday);
		System.out.println("isCustomized flag (DB): " + customer.isCustomized());
		System.out.println("==== DEBUG END ====");


		// Boolean isCommon = isCommon(customer.isPragnent(), customer.isAlergic(), isCustomizedToday);
		Boolean isCommon = isCommon(
							customer.isPragnent(),
							customer.isAlergic(),
							// customer.isCustomized(),
							isCustomizedToday
						);


		Integer packageTypeId = null;
		System.out.println(day.getValue());
		System.out.println(weekday);
		System.out.println(businessDate);
		System.out.println(isCommon);
		System.out.println(customer.isPragnent());
		System.out.println(customer.isAlergic());
		System.out.println(isCustomizedToday);

		// Only fetch packageTypeId if it's not customized
		if (Boolean.FALSE.equals(isCustomizedToday)) {
			Integer optionalTypeId = PackageTypeRepo.getPackageTypeId(customer.isPragnent(), customer.isAlergic(),
					isCustomizedToday, isCommon, customer.getPackDetailsId()).orElse(0);

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
			response = customizedpackagedetailsRepo.findDetailedPack(customerId, weekday, businessDate);
		} else if (Boolean.TRUE.equals(customer.isAlergic())) {
			response = AllergicPackageDetailsRepo.findDetailedPack(customerId, weekday);
		} else if (Boolean.TRUE.equals(customer.isPragnent())) {
			response = commonandPragnentpackDetailsRepo.getPackageItemView(packageTypeId, weekday);
		} else {
			response = commonandPragnentpackDetailsRepo.getPackageItemView(packageTypeId, weekday);
		}
		List<PackageResponseDto> responseList = new ArrayList<>();
		Map<String, Integer> indexMap = getIndexMap();

		for (Object[] row : response) {

			PackageResponseDto dto = new PackageResponseDto();

			// ==========================
			// EGG ADDED
			// ==========================
			dto.setEggAdded(Boolean.TRUE.equals((Boolean) getValue(row, indexMap, "isEggAdded")));

			// ==========================
			// EGG OR SEED (SINGLE OBJECT)
			// ==========================
			IngredientDto eggOrSeed = null;
			Object eggNameObj = getValue(row, indexMap, "eggOrSeedName");


			if (eggNameObj != null) {
				eggOrSeed = new IngredientDto();
				eggOrSeed.setId(getLong(row, indexMap, "eggOrSeedId"));
				eggOrSeed.setName(eggNameObj.toString());

				eggOrSeed.setCategory(getValue(row, indexMap, "eggOrSeedCategory").toString());
				Object w = getValue(row, indexMap, "eggOrSeedWeight");
				eggOrSeed.setWeight(w != null ? w.toString() : null);

				eggOrSeed.setProtein(getBD(row, indexMap, "eggOrSeedProtein"));
				eggOrSeed.setFiber(getBD(row, indexMap, "eggOrSeedFiber"));
				eggOrSeed.setFat(getBD(row, indexMap, "eggOrSeedFat"));
				eggOrSeed.setCarboHydreate(getBD(row, indexMap, "eggOrSeedCarboHydreate"));
				eggOrSeed.setSugar(getBD(row, indexMap, "eggOrSeedSugar"));
			}
			dto.setEggOrSeed(eggOrSeed);

			// ==========================
			// FRUITS
			// ==========================
			List<IngredientDto> fruits = new ArrayList<>();

			for (int i = 1; i <= 6; i++) {
				Object nameObj = getValue(row, indexMap, "fruit" + i + "Name");
				if (nameObj == null)
					continue;

				IngredientDto fruit = new IngredientDto();
				fruit.setId(
					    getLong(row, indexMap, "fruit" + i + "Id")
					);
					fruit.setName(nameObj.toString());

				fruit.setCategory(getValue(row, indexMap, "fruit" + i + "Category").toString());
				Object w = getValue(row, indexMap, "fruit" + i + "Weight");
				fruit.setWeight(w != null ? w.toString() : null);

				fruit.setProtein(getBD(row, indexMap, "fruit" + i + "Protein"));
				fruit.setFiber(getBD(row, indexMap, "fruit" + i + "Fiber"));
				fruit.setFat(getBD(row, indexMap, "fruit" + i + "Fat"));
				fruit.setCarboHydreate(getBD(row, indexMap, "fruit" + i + "CarboHydreate"));
				fruit.setSugar(getBD(row, indexMap, "fruit" + i + "Sugar"));

				fruits.add(fruit);
			}
			dto.setFruits(fruits);

			// ==========================
			// NUTS
			// ==========================
			List<IngredientDto> nuts = new ArrayList<>();

			for (int i = 1; i <= 5; i++) {
				Object nameObj = getValue(row, indexMap, "nut" + i + "Name");
				if (nameObj == null)
					continue;

				IngredientDto nut = new IngredientDto();
				nut.setId(
					    getLong(row, indexMap, "nut" + i + "Id")
					);

				nut.setName(nameObj.toString());

				nut.setCategory(getValue(row, indexMap, "nut" + i + "Category").toString());

				Object w = getValue(row, indexMap, "nut" + i + "Weight");
				nut.setWeight(w != null ? w.toString() : null);

				nut.setProtein(getBD(row, indexMap, "nut" + i + "Protein"));
				nut.setFiber(getBD(row, indexMap, "nut" + i + "Fiber"));
				nut.setFat(getBD(row, indexMap, "nut" + i + "Fat"));
				nut.setCarboHydreate(getBD(row, indexMap, "nut" + i + "CarboHydreate"));
				nut.setSugar(getBD(row, indexMap, "nut" + i + "Sugar"));

				nuts.add(nut);
			}
			dto.setNuts(nuts);

			// ==========================
			// OPTIONALS
			// ==========================
			List<OptionalIngredientDto> optionals = new ArrayList<>();

			for (int i = 1; i <= 6; i++) {
				Object nameObj = getValue(row, indexMap, "optional" + i + "Name");
				if (nameObj == null)
					continue;

				OptionalIngredientDto opt = new OptionalIngredientDto();
				opt.setId(
					    getLong(row, indexMap, "optional" + i + "Id")
					);

				opt.setName(nameObj.toString());
				opt.setCategory(getValue(row, indexMap, "optional" + i + "Category").toString());
				Object w = getValue(row, indexMap, "optional" + i + "Weight");
				opt.setWeight(w != null ? w.toString() : null);

				opt.setIsFruit(Boolean.TRUE.equals((Boolean) getValue(row, indexMap, "Optional" + i + "IsFruit")));

				opt.setFiber(getBD(row, indexMap, "optional" + i + "Fiber"));
				opt.setProtein(getBD(row, indexMap, "optional" + i + "Protein"));
				opt.setFat(getBD(row, indexMap, "optional" + i + "Fat"));
				opt.setCarboHydreate(getBD(row, indexMap, "optional" + i + "CarboHydreate"));
				opt.setSugar(getBD(row, indexMap, "optional" + i + "Sugar"));

				optionals.add(opt);
			}
			dto.setOptionals(optionals);

			// ==========================
			// SANDWICH
			// ==========================
			IngredientDto sandwich = null;
			Object sandwichNameObj = getValue(row, indexMap, "sandwichName");

			if (sandwichNameObj != null) {
				sandwich = new IngredientDto();
				sandwich.setId(
					    getLong(row, indexMap, "sandwichId")
					);

				sandwich.setName(sandwichNameObj.toString());
				sandwich.setCategory(getValue(row, indexMap, "sandwichCategory").toString());
				sandwich.setWeight(null); // no weight

				sandwich.setProtein(getBD(row, indexMap, "sandwichProtein"));
				sandwich.setFiber(getBD(row, indexMap, "sandwichFiber"));
				sandwich.setFat(getBD(row, indexMap, "sandwichFat"));
				sandwich.setCarboHydreate(getBD(row, indexMap, "sandwichCarboHydreate"));
				sandwich.setSugar(getBD(row, indexMap, "sandwichSugar"));
			}
			dto.setSandwich(sandwich);

			responseList.add(dto);
		}

		return responseList;

	}

	private Long getLong(Object[] row, Map<String, Integer> indexMap, String key) {

		Object val = getValue(row, indexMap, key);
		if (val == null)
			return null;

		if (val instanceof Integer)
			return ((Integer) val).longValue();

		if (val instanceof Long)
			return (Long) val;

		return Long.valueOf(val.toString());
	}

	private Object getValue(Object[] row, Map<String, Integer> map, String key) {
		Integer idx = map.get(key);
		if (idx == null || idx < 0 || idx >= row.length) {
			return null;
		}
		return row[idx];
	}

	private BigDecimal getBD(Object[] row, Map<String, Integer> map, String key) {
		return toBigDecimal(getValue(row, map, key));
	}

	private BigDecimal toBigDecimal(Object value) {
		if (value == null)
			return BigDecimal.ZERO;
		if (value instanceof BigDecimal)
			return (BigDecimal) value;
		if (value instanceof Number)
			return BigDecimal.valueOf(((Number) value).doubleValue());
		try {
			return new BigDecimal(value.toString().trim());
		} catch (NumberFormatException e) {
			throw new RuntimeException("Invalid number: " + value);
		}
	}

	private Map<String, Integer> getIndexMap() {
		Map<String, Integer> map = new HashMap<>();
		int i = 0;

		map.put("isEggAdded", i++);
		map.put("eggOrSeedId", i++);
		map.put("eggOrSeedName", i++);
		map.put("eggOrSeedCategory", i++);
		map.put("eggOrSeedWeight", i++);
		map.put("eggOrSeedProtein", i++);
		map.put("eggOrSeedFiber", i++);
		map.put("eggOrSeedFat", i++);
		map.put("eggOrSeedCarboHydreate", i++); // Corrected spelling
		map.put("eggOrSeedSugar", i++); // Corrected spelling

		for (int j = 1; j <= 6; j++) {
			map.put("fruit" + j + "Id", i++);
			map.put("fruit" + j + "Name", i++);
			map.put("fruit" + j + "Category", i++);
			map.put("fruit" + j + "Weight", i++);
			map.put("fruit" + j + "Protein", i++);
			map.put("fruit" + j + "Fiber", i++);
			map.put("fruit" + j + "Fat", i++);
			map.put("fruit" + j + "CarboHydreate", i++);
			map.put("fruit" + j + "Sugar", i++);
		}

		for (int j = 1; j <= 5; j++) {
			map.put("nut" + j + "Id", i++);
			map.put("nut" + j + "Name", i++);
			map.put("nut" + j + "Category", i++);
			map.put("nut" + j + "Weight", i++);
			map.put("nut" + j + "Protein", i++);
			map.put("nut" + j + "Fiber", i++);
			map.put("nut" + j + "Fat", i++);
			map.put("nut" + j + "CarboHydreate", i++);
			map.put("nut" + j + "Sugar", i++);
		}

		for (int j = 1; j <= 6; j++) {
			map.put("optional" + j + "Id", i++);
			map.put("optional" + j + "Name", i++);
			map.put("optional" + j + "Category", i++);
			map.put("optional" + j + "Weight", i++);
			map.put("Optional" + j + "IsFruit", i++); // Keep case consistent
			map.put("optional" + j + "Protein", i++);
			map.put("optional" + j + "Fiber", i++);
			map.put("optional" + j + "Fat", i++);
			map.put("optional" + j + "CarboHydreate", i++);
			map.put("optional" + j + "Sugar", i++);
		}

		// ==========================
		// SANDWICH (SINGLE ITEM)
		// ==========================
		map.put("sandwichId", i++);
		map.put("sandwichName", i++);
		map.put("sandwichCategory", i++);
		map.put("sandwichProtein", i++);
		map.put("sandwichFiber", i++);
		map.put("sandwichFat", i++);
		map.put("sandwichCarboHydreate", i++);
		map.put("sandwichSugar", i++);

		return map;
	}

	public boolean mapToEntity(CustomizedPackageRequest request) {

		if (request.getCustomerId() == 0) {
			throw new RuntimeException("Customer not found");
		}

		System.out.println("request" + request.getOptional());
		// Allowed Time Range
		// LocalTime startTime = LocalTime.of(9, 0);
		// LocalTime endTime = LocalTime.of(19, 0); // 7 PM

		// LocalTime current = LocalTime.now();

		// if (current.isBefore(startTime) || current.isAfter(endTime)) {
		// 	throw new RuntimeException(
		// 			"Today's customization time is over. You can customize only between 9 AM to 7 PM.");
		// } 
		LocalTime startTime = LocalTime.of(9, 30);   // 9:30 AM
		LocalTime endTime = LocalTime.of(23, 30);   // 7:30 PM

		LocalTime current = LocalTime.now(ZoneId.of("Asia/Kolkata"));
		
		System.out.println("Current Time: " + current);

		if (current.isBefore(startTime) || current.isAfter(endTime)) {
			throw new RuntimeException(
				"Customization allowed only between 9:30 AM to 7:30 PM");
		}
		
		else {
			CustomizedPackageDetails entity = new CustomizedPackageDetails();

			LocalDateTime businessDate = LocalDateTime.now().plusDays(1);
			System.out.println(businessDate);
			 DayOfWeek day = LocalDateTime.now().getDayOfWeek();

			//DayOfWeek day = businessDate.getDayOfWeek();
			int weekday = 0;
			if (day.getValue() == 7) {
				weekday = 1;
			} else {
				weekday = day.getValue() + 1;
			}

			// Optional<CustomizedPackageDetails> data = customizedpackagedetailsRepo
			// 		.findByCustomerIdAndCustomizedDate(request.getCustomerId(), businessDate);

				
			Optional<CustomizedPackageDetails> data = customizedpackagedetailsRepo
			 		.findByCustomerIdAndCustomizedDate(request.getCustomerId(), businessDate);



			data.ifPresentOrElse(existing -> {
				System.out.println(existing);
				customizedpackagedetailsRepo.deleteById(existing.getId());
			}, () -> System.out.println("No existing customization found for this customer & date."));

			System.out.println(request.getCustomerId());
			entity.setCustomerId(request.getCustomerId());
			//entity.setCustomizedDate(businessDate);
			entity.setWeekdaysId(weekday);
			entity.setCustomizedDate(businessDate);
			// st.isEggAdded());
			// entity.setIsEggAdded(request.getIsEggAdded());
			entity.setIsEggAdded(request.isEggAdded()); 
			// entity.setIsEggAdded(request.getEggAdded());
      
			// Egg or seed
//			if (request.getEggOrSeed() != null && request.getEggOrSeed().getId() != null) {
//			    entity.setEggOrSeed(request.getEggOrSeed().getId().intValue());
//			    // entity.setEggOrSeedWeight(request.getEggOrSeed().getWeight());
//				entity.setEggOrSeedWeight(String.valueOf(request.getEggOrSeed().getWeight()));
//			}
			if (request.getEggOrSeed() != null) {
			    Integer id = resolveId(
			        request.getEggOrSeed().getId(),
			        request.getEggOrSeed().getName()
			    );

			    if (id != null) {
			        entity.setEggOrSeed(id);
			        entity.setEggOrSeedWeight(String.valueOf(request.getEggOrSeed().getWeight()));
			    }
			}


			// Sandwich
//			if (request.getSandwich() != null && request.getSandwich().getId() != null) {
//			    entity.setSandwichId(request.getSandwich().getId().intValue());
//			}
			
			if (request.getSandwich() != null) {
			    Integer id = resolveId(
			        request.getSandwich().getId(),
			        request.getSandwich().getName()
			    );

			    if (id != null) {
			        entity.setSandwichId(id);
			    }
			}

			// Fruits
			List<IngredientDto> fruits = request.getFruits();

			if (fruits != null) {
			    for (int i = 0; i < Math.min(fruits.size(), 6); i++) {

			        IngredientDto fruit = fruits.get(i);

			       // if (fruit.getId() == null) continue;
			        
			        Integer fruitId = resolveId(fruit.getId(), fruit.getName());
			        if (fruitId == null) continue; // just safety

			        // fruitId = fruit.getId().intValue();
			        String weight = fruit.getWeight();

			        switch (i) {
			            case 0 -> {
			                entity.setFruit1Id(fruitId);
			                entity.setFruit1Weight(weight);
			            }
			            case 1 -> {
			                entity.setFruit2Id(fruitId);
			                entity.setFruit2Weight(weight);
			            }
			            case 2 -> {
			                entity.setFruit3Id(fruitId);
			                entity.setFruit3Weight(weight);
			            }
			            case 3 -> {
			                entity.setFruit4Id(fruitId);
			                entity.setFruit4Weight(weight);
			            }
			            case 4 -> {
			                entity.setFruit5Id(fruitId);
			                entity.setFruit5Weight(weight);
			            }
			            case 5 -> {
			                entity.setFruit6Id(fruitId);
			                entity.setFruit6Weight(weight);
			            }
			        }
			    }
			}


			// Nuts
			List<IngredientDto> nuts = request.getNuts();

			if (nuts != null) {
			    for (int i = 0; i < Math.min(nuts.size(), 5); i++) {

			        IngredientDto nut = nuts.get(i);

//			        if (nut.getId() == null) continue;
//
//			        int nutId = nut.getId().intValue();
			        
			        Integer nutId = resolveId(nut.getId(), nut.getName());
			        if (nutId == null) continue;
			        
			        String weight = nut.getWeight();

			        switch (i) {
			            case 0 -> {
			                entity.setNut1Id(nutId);
			                entity.setNut1Weight(weight);
			            }
			            case 1 -> {
			                entity.setNut2Id(nutId);
			                entity.setNut2Weight(weight);
			            }
			            case 2 -> {
			                entity.setNut3Id(nutId);
			                entity.setNut3Weight(weight);
			            }
			            case 3 -> {
			                entity.setNut4Id(nutId);
			                entity.setNut4Weight(weight);
			            }
			            case 4 -> {
			                entity.setNut5Id(nutId);
			                entity.setNut5Weight(weight);
			            }
			        }
			    }
			}

			List<OptionalIngredientDto> optionalItems = request.getOptional();

			if (optionalItems != null) {

			    for (int i = 0; i < Math.min(optionalItems.size(), 6); i++) {

			        OptionalIngredientDto opt = optionalItems.get(i);

//			        if (opt == null || opt.getId() == null) continue;
//
//			        int id = opt.getId().intValue();
			        
			       Integer id = resolveId(opt.getId(), opt.getName());
			        if (id == null) continue;
			        
			        String weight = opt.getWeight() != null ? opt.getWeight() : "0";
			        Boolean isFruit = opt.getIsFruit() != null ? opt.getIsFruit() : false;

			        switch (i) {
			            case 0 -> {
			                entity.setOptional1Id(id);
			                entity.setOptional1Weight(weight);
			                entity.setOptional1IsFruit(isFruit);
			            }
			            case 1 -> {
			                entity.setOptional2Id(id);
			                entity.setOptional2Weight(weight);
			                entity.setOptional2IsFruit(isFruit);
			            }
			            case 2 -> {
			                entity.setOptional3Id(id);
			                entity.setOptional3Weight(weight);
			                entity.setOptional3IsFruit(isFruit);
			            }
			            case 3 -> {
			                entity.setOptional4Id(id);
			                entity.setOptional4Weight(weight);
			                entity.setOptional4IsFruit(isFruit);
			            }
			            case 4 -> {
			                entity.setOptional5Id(id);
			                entity.setOptional5Weight(weight);
			                entity.setOptional5IsFruit(isFruit);
			            }
			            case 5 -> {
			                entity.setOptional6Id(id);
			                entity.setOptional6Weight(weight);
			                entity.setOptional6IsFruit(isFruit);
			            }
			        }
			    }
			}

			System.out.println("user" + entity.getOptional1Id());
			System.out.println(entity.getOptional1IsFruit());

			entity.setStatusId(1);
			entity.setCreatedBy("user");
			entity.setCreatedTime(LocalDateTime.now());

			CustomizedPackageDetails saved = customizedpackagedetailsRepo.save(entity);

			if (saved.getId() != null) {
				CustomerDetails customerDetails = new CustomerDetails();
				customerDetails.setId(request.getCustomerId());
				customerDetails.setCustomized(true);
				customerDetails.setLastCustomizedDate(businessDate);
				customerDetails.setModefiedBy("user");
				customerDetails.setModefiedTime(LocalDate.now());
				customerDetailsRepo.updateCustomization(
   					 request.getCustomerId(),
   				         customerDetails.isCustomized(),
    					 businessDate,
 				         "user",
 				          LocalDate.now()
					);

			return true;
		}
	 return false;
        }
    }
	public int getFruitOrNutId(String fruitAndNuts) {
		System.out.println(fruitAndNuts);
		return lkpFruitAndNutsRepo.findByFruitAndNutsIgnoreCase(fruitAndNuts).map(LkpFruitAndNuts::getId)
				.orElseThrow(() -> new RuntimeException("Fruit/Nut not found: " + fruitAndNuts));
	}
	private Integer resolveId(Long id, String name) {
	    if (id != null) {
	        return id.intValue(); //  use ID directly
	    }
	    if (name != null && !name.isBlank()) {
	        return getFruitOrNutId(name); //  fallback to name
	    }
	    return null;
	}
	
}    