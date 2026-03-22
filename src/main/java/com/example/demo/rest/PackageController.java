package com.example.demo.rest;

import java.io.ByteArrayOutputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.FinalPackDto;
import com.example.demo.dto.PackNamesRequest;
import com.example.demo.dto.PlanCountDto;
import com.example.demo.entity.DailyTagLine;
import com.example.demo.projection.QrCodeProjection;
import com.example.demo.repo.AvailablePromoCodeRepo;
import com.example.demo.repo.PackagingRepo;
import com.example.demo.service.PackagingService;
import com.example.demo.util.QRCodeGenerator;

@RestController
@RequestMapping("/api")
public class PackageController {

	@Autowired
	private PackagingService packagingService;

	@Autowired
	private PackagingRepo packagingRepo;

	@Autowired
	private QRCodeGenerator qRCodeGenerator;
	

	
	@PostMapping("/batch-generate")
	public ResponseEntity<String> batchGenerateQR(@RequestParam int districtId) {
		try {
			packagingService.generateCodesForPacking(districtId);
			return ResponseEntity.ok("QR codes generated successfully for today.");
		} catch (Exception e) {
			return ResponseEntity.status(500).body("Error: " + e.getMessage());
		}
	}
//	@PostMapping("/qr-generate")
//	public void printBatchQRCodes() throws Exception {
//		List<QrCodeProjection> qrDataList = packagingRepo.fetchAllQrAndCodes();
//		qRCodeGenerator.generateQrPrintPdf(qrDataList, "C:\\Users\\skira\\Downloads\\pdf");
//	}
	@GetMapping("/download-qrcodes")
	public  ResponseEntity<List<String>> downloadQrCodes(@RequestParam int districtId) throws Exception {
	    List<QrCodeProjection> data = packagingRepo.fetchAllQrAndCodes(districtId);
	    List<String> finalImageUrl =packagingService.createImage(data);
        System.out.println(finalImageUrl);
        return ResponseEntity.ok(finalImageUrl);

//	    ByteArrayOutputStream out = new ByteArrayOutputStream();
//	    QRCodeGenerator.generateQrPrintPdf(data, out); // updated method
//	    byte[] pdfBytes = out.toByteArray();
//
//	    HttpHeaders headers = new HttpHeaders();
//	    headers.setContentType(MediaType.APPLICATION_PDF);
//	    headers.setContentDispositionFormData("attachment", "qrcodes.pdf");

	    //return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
	}
	
	 @GetMapping("/getPackageBoxlist")
	    public List<FinalPackDto> getPackageBoxlist(@RequestParam int districtId,@RequestParam long boxnumber) {
		 return packagingService.getPackageBoxlist(districtId,boxnumber);
	    }
	 
	 @PostMapping("/savePackedProduct/by-names")
	 public ResponseEntity<String> updatePackedItemsByNames(@RequestBody PackNamesRequest request) {
	     packagingService.updatePackedStatusByNames(
	         request.getBoxNumber(),
	         request.getPackerId(),
	         request.getProductNames(),
	         request.getDistrictId()
	     );
	     return ResponseEntity.ok("Packed items updated.");
	 }
	 @GetMapping("/getNumberOfBox")
	    public List<PlanCountDto> getNumberOfBox(@RequestParam int districtId) {
		 return packagingService.getNumberOfBox(districtId);
	    }
	 
	 @GetMapping("/getDeliveryPersonDetails")
	    public String getDeliveryPersonDetails(@RequestParam String PhoneNumber) {
		 return packagingService.getDeliveryPersonDetails(PhoneNumber);
	    }
	 
	 @GetMapping("/PackVerification")
	    public String PackVerification(@RequestParam int districtId,@RequestParam long boxnumber) {
		 return packagingService.PackVerification(districtId,boxnumber);
	    }
	 
}
