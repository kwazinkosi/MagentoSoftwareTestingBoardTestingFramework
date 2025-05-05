package utils.dataproviders.factory;

import utils.dataproviders.interfaces.SheetConfig;
import utils.dataproviders.interfaces.TestDataModel;
import utils.dataproviders.interfaces.TestDataProvider;
import utils.dataproviders.models.SignUpTestData;
import utils.dataproviders.models.LoginTestData;
import utils.dataproviders.models.NavbarTestData;
import utils.dataproviders.models.SearchTestData;

import java.util.Map;

import utils.dataproviders.GenericExcelDataProvider;
import utils.dataproviders.concrete.SignUpSheetConfig;
import utils.dataproviders.concrete.LoginSheetConfig;
import utils.dataproviders.concrete.NavbarSheetConfig;
import utils.dataproviders.concrete.SearchSheetConfig;


public class DataProviderFactory {
	
    private static final Map<Class<? extends TestDataModel>, Object> CONFIG_MAP = Map.of(
    		
        LoginTestData.class, new LoginSheetConfig(),
        NavbarTestData.class, new NavbarSheetConfig(),
        SearchTestData.class, new SearchSheetConfig(),
        SignUpTestData.class, new SignUpSheetConfig()
    );

    @SuppressWarnings("unchecked")
    public static <T> TestDataProvider<T> createProvider(Class<T> dataClass, String format) {
    	
        SheetConfig<T> config = (SheetConfig<T>) CONFIG_MAP.get(dataClass);
        if (config == null) {
            throw new IllegalArgumentException("No config found for: " + dataClass);
        }
        return new GenericExcelDataProvider<>(config);
    }
}