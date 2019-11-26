
package csueb.project.app.dao;

import java.util.List;
import java.util.Map;

import csueb.project.app.dto.BusinessCardInput;
import csueb.project.app.dto.BusinessCardOutput;
import csueb.project.app.dto.UserLoginRequest;
import csueb.project.app.dto.UserSignInRequest;

public interface BusinessCardDAO {
	
	Map<String, String> createUser(UserSignInRequest userRequest);
	
	Map<String, String> getUserDetails(UserLoginRequest userLoginRequest);
	
	Map<String, String> insertCardDetails(BusinessCardInput businessCardInput);
	
	List<BusinessCardOutput> getUserFiles(String emailId);
	
	Map<String, String> updateFileDescription(String emailId, String fileName, String fileDescription);
	
	BusinessCardOutput getFileDetailsBasedOnId(Integer id);
	
	Map<String, String> deleteCard(Integer id);
	
	List<BusinessCardOutput> searchBusinessCard(String userEmailId, Integer searchType, String searchInput);
	
	Map<String, String> getBusinessCardUrls();
	
	Map<String, String> getSocialUserDetails(String emailId);
	
	Map<String, String> createSocialLoginUser(String emailId, String firstName, String lastName);
	
}
