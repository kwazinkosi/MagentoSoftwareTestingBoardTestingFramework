package utils.dataproviders;

import utils.dataproviders.factory.DataProviderFactory;
import utils.dataproviders.interfaces.TestDataProvider;
import utils.dataproviders.models.SignUpTestData;
import utils.dataproviders.models.LoginTestData;
import utils.dataproviders.models.NavbarTestData;
import utils.dataproviders.models.SearchTestData;
import exceptions.DataProviderException;
import org.testng.annotations.DataProvider;
import java.util.List;
public class MagentoDataProviders {

    // Configuration constants
    private static final String TEST_DATA_FILE = "/data/test-data.xlsx";
    private static final String DATA_FORMAT = "excel";
    

    @DataProvider(name = "loginData")
    public static Object[][] getLoginData() {
       
    	try {
            TestDataProvider<LoginTestData> provider = DataProviderFactory.createProvider(
            		LoginTestData.class, 
                DATA_FORMAT
            );
            List<LoginTestData> testData = provider.getTestData(TEST_DATA_FILE, "LoginData");
            return testData.stream()
                .map(data -> new Object[]{data})
                .toArray(Object[][]::new);
            
        } catch (Exception e) {
            throw new DataProviderException("Failed to load login test data", e);
        }
    }
    
    @DataProvider(name = "navbarData")
    public static Object[][] getMenuItemData() {
        
    	try {
            TestDataProvider<NavbarTestData> provider = DataProviderFactory.createProvider(
            		NavbarTestData.class, 
                DATA_FORMAT
            );

            List<NavbarTestData> testData = provider.getTestData(TEST_DATA_FILE, "NavbarData");
            
            return testData.stream()
                .map(data -> new Object[]{data})
                .toArray(Object[][]::new);
            
        } catch (Exception e) {
            throw new DataProviderException("Failed to load navItem test data", e);
        }
    }
    
    @DataProvider(name = "searchData")
    public static Object[][] getSocialsData() {
        
    	try {
            TestDataProvider<SearchTestData> provider = DataProviderFactory.createProvider(
            		SearchTestData.class, 
                DATA_FORMAT
            );

            List<SearchTestData> testData = provider.getTestData(TEST_DATA_FILE, "SearchData");
            
            return testData.stream()
                .map(data -> new Object[]{data})
                .toArray(Object[][]::new);
            
        } catch (Exception e) {
            throw new DataProviderException("Failed to load search test data", e);
        }
    }
    
    @DataProvider(name = "signUpData")
    public static Object[][] getCheckoutData() {
        
    	try {
            TestDataProvider<SignUpTestData> provider = DataProviderFactory.createProvider(
            		SignUpTestData.class, 
                DATA_FORMAT
            );

            List<SignUpTestData> testData = provider.getTestData(TEST_DATA_FILE, "SignUpData");
            
            return testData.stream()
                .map(data -> new Object[]{data})
                .toArray(Object[][]::new);
            
        } catch (Exception e) {
            throw new DataProviderException("Failed to load signup test data", e);
        }
    }
}