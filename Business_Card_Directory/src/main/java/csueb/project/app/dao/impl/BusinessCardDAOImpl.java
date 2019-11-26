
package csueb.project.app.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import csueb.project.app.dao.BusinessCardDAO;
import csueb.project.app.dto.BusinessCardInput;
import csueb.project.app.dto.BusinessCardOutput;
import csueb.project.app.dto.UserDetailsDTO;
import csueb.project.app.dto.UserLoginRequest;
import csueb.project.app.dto.UserSignInRequest;

@Repository("mysql")
public class BusinessCardDAOImpl implements BusinessCardDAO{
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public Map<String, String> createUser(UserSignInRequest userRequest){
		
		HashMap<String, String> outputMap = new HashMap<>();
		
		try {
			String sql = "INSERT INTO CUSTOMER_INFO (FirstName, LastName, EmailId, Password, SecurityQ1, SecurityQ2, SecurityV1, SecurityV2) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
			
			jdbcTemplate.update(sql, userRequest.getFirstName(), userRequest.getLastName(), 
					userRequest.getEmailId(), userRequest.getPassword(), userRequest.getSecurityQ1(), 
					userRequest.getSecurityQ2(), userRequest.getSecurityV1(), userRequest.getSecurityV2());
			
			outputMap.put("status", "true");
			
		}catch (Exception e)
		{
			outputMap.put("status", "false");
		}
		return outputMap;
	}
	
	@Override
	public Map<String, String> getUserDetails(UserLoginRequest userLoginRequest){
		
		HashMap<String, String> outputMap = new HashMap<>();
		
		try {
			String sql = "SELECT * FROM CUSTOMER_INFO WHERE EmailId = ? AND Password = ?";
			
			UserDetailsDTO userDetailsDTO = (UserDetailsDTO) jdbcTemplate.queryForObject(
					sql, new Object[] { userLoginRequest.getEmailId(), userLoginRequest.getPassword() }, 
					new BeanPropertyRowMapper(UserDetailsDTO.class));
			
			outputMap.put("status", "true");
			outputMap.put("firstname", userDetailsDTO.getFirstName());
			outputMap.put("lastname", userDetailsDTO.getLastName());
			outputMap .put("emailid", userDetailsDTO.getEmailId());
		}catch (Exception e)
		{
			outputMap.put("status", "false");
		}
			
		return outputMap;
	}

	@Override
	public Map<String, String> insertCardDetails(BusinessCardInput businessCardInput) {
		
		HashMap<String, String> outputMap = new HashMap<>();
		
		try {
			String sql = "INSERT INTO BUSINESS_CARD_DETAILS (EmailId, FileName, FileDescription, CardContactName,"
					+ " CardContactEmail, CardContactMobile, CardOrganization, CreatedTime) VALUES (?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP())";
			
			jdbcTemplate.update(sql, businessCardInput.getEmailId(), businessCardInput.getFileName(), businessCardInput.getFileDescription(),
					businessCardInput.getContactName(), businessCardInput.getContactEmailId(), businessCardInput.getContactNumber(),
					businessCardInput.getOrganization());
			
			outputMap.put("status", "true");
			
		}catch (Exception e)
		{
			outputMap.put("status", "false");
		}
		return outputMap;
	}

	@Override
	public List<BusinessCardOutput> getUserFiles(String emailId) {
		
		String sql = "SELECT * FROM BUSINESS_CARD_DETAILS where EmailId = ?";
		
		List<BusinessCardOutput> businessCardList = new ArrayList<BusinessCardOutput>();
		
		List<java.util.Map<String, Object>> result = jdbcTemplate.queryForList(sql, emailId);
		
		for(java.util.Map<String, Object> obj : result)
		{
			BusinessCardOutput businessCardDetails = new BusinessCardOutput();
			
			businessCardDetails.setId((Integer)obj.get("ID"));
			businessCardDetails.setContactEmailId((String)obj.get("CardContactEmail"));
			businessCardDetails.setContactName((String)obj.get("CardContactName"));
			businessCardDetails.setContactNumber((String)obj.get("CardContactMobile"));
			businessCardDetails.setOrganization((String)obj.get("CardOrganization"));
			businessCardDetails.setFileName((String)obj.get("FileName"));
			businessCardDetails.setFileDescription((String)obj.get("FileDescription"));
			businessCardDetails.setEmailId((String)obj.get("EmailId"));
			businessCardList.add(businessCardDetails);
		}
		
		return businessCardList;
	}

	@Override
	public Map<String, String> updateFileDescription(String emailId, String fileName, String fileDescription) {
		
		HashMap<String, String> outputMap = new HashMap<>();
		
		try {
			String sql = "UPDATE BUSINESS_CARD_DETAILS SET FileDescription = ? "
					+ " WHERE EmailId = ? AND FileName = ?";
			
			jdbcTemplate.update(sql, fileDescription, emailId, fileName);
			
			outputMap.put("status", "true");
			
		}catch (Exception e)
		{
			outputMap.put("status", "false");
		}
		return outputMap;
	}

	@Override
	public BusinessCardOutput getFileDetailsBasedOnId(Integer id) {
		
		String sql = "SELECT * FROM BUSINESS_CARD_DETAILS where ID = ?";
		
		BusinessCardOutput businessCardDetails = (BusinessCardOutput) jdbcTemplate.queryForObject(
				sql, new Object[] { id }, 
				new BeanPropertyRowMapper(BusinessCardOutput.class));
		
		return businessCardDetails;
	}

	@Override
	public Map<String, String> deleteCard(Integer id) {
		
		HashMap<String, String> outputMap = new HashMap<>();
		try {
		String sql = "DELETE FROM BUSINESS_CARD_DETAILS where ID = ?";
		
		jdbcTemplate.update(sql, id);
		
		outputMap.put("status", "true");
		
		}catch (Exception e)
		{
			outputMap.put("status", "false");
		}
		return outputMap;
		
	}

	@Override
	public List<BusinessCardOutput> searchBusinessCard(String userEmailId, Integer searchType, String searchInput) {
		
		String sql = null;
		List<BusinessCardOutput> businessCardList = new ArrayList<BusinessCardOutput>();
		String searchValue = "%"+searchInput+"%";
		
		if (searchType == 1) {
			
			sql = "SELECT * FROM BUSINESS_CARD_DETAILS where EmailId = ? and CardContactName LIKE ? ";
		}
		else {
			
			sql = "SELECT * FROM BUSINESS_CARD_DETAILS where EmailId = ? and CardOrganization LIKE ? ";
			
		}
		
		List<java.util.Map<String, Object>> result = jdbcTemplate.queryForList(sql, userEmailId, searchValue);
		
		for(java.util.Map<String, Object> obj : result)
		{
			BusinessCardOutput businessCardDetails = new BusinessCardOutput();
			
			businessCardDetails.setId((Integer)obj.get("ID"));
			businessCardDetails.setContactEmailId((String)obj.get("CardContactEmail"));
			businessCardDetails.setContactName((String)obj.get("CardContactName"));
			businessCardDetails.setContactNumber((String)obj.get("CardContactMobile"));
			businessCardDetails.setOrganization((String)obj.get("CardOrganization"));
			businessCardDetails.setFileName((String)obj.get("FileName"));
			businessCardDetails.setFileDescription((String)obj.get("FileDescription"));
			businessCardDetails.setEmailId((String)obj.get("EmailId"));
			businessCardList.add(businessCardDetails);
		}
		
		return businessCardList;
	}

	@Override
	public Map<String, String> getBusinessCardUrls() {
		
		HashMap<String, String> outputMap = new HashMap<>();
		try {
		String sql = "SELECT CloudFrontURL FROM BUSINESS_CARD_URLS";
		
		String sql1 = "SELECT CognitoID FROM BUSINESS_CARD_URLS";
		
		String cloudFronURl = jdbcTemplate.queryForObject(sql, String.class);
		
		String cognitoID = jdbcTemplate.queryForObject(sql1, String.class);
		
		outputMap.put("status", "true");
		outputMap.put("CloudFrontUrl", cloudFronURl);
		outputMap.put("CognitoID", cognitoID);
		
		}catch (Exception e)
		{
			outputMap.put("status", "false");
		}
		return outputMap;
	}
	
	@Override
	public Map<String, String> getSocialUserDetails(String emailId) {
		
		HashMap<String, String> outputMap = new HashMap<>();
		try {
			String sql = "SELECT * FROM CUSTOMER_INFO WHERE EmailId = ?";
			
			UserDetailsDTO userDetailsDTO = (UserDetailsDTO) jdbcTemplate.queryForObject(
					sql, new Object[] { emailId}, 
					new BeanPropertyRowMapper(UserDetailsDTO.class));
			
			outputMap.put("status", "true");
			outputMap.put("dbrecord", "success");
			outputMap .put("emailid", userDetailsDTO.getEmailId());
			
		}catch (Exception e)
		{
			e.printStackTrace();
			outputMap.put("status", "false");
			outputMap.put("dbrecord", "error");
			outputMap .put("emailid", null);
		}
		return outputMap;
	}
	
	@Override
	public Map<String, String> createSocialLoginUser(String emailId, String firstName, String lastName) {
		
		HashMap<String, String> outputMap = new HashMap<>();
		
		try {
			String sql = "INSERT INTO CUSTOMER_INFO (FirstName, LastName, EmailId) "
					+ "VALUES (?, ?, ?)";
			
			jdbcTemplate.update(sql, firstName, lastName, emailId);
			
			outputMap.put("status", "true");
			
		}catch (Exception e)
		{
			e.printStackTrace();
			outputMap.put("status", "false");
		}
		return outputMap;
	}
}
