package com.ysdc.comet.common.network.config


object NetworkConstants {

    //SharedFragmentModule constant
    const val TIMEOUT_IN_SECONDS: Long = 20
    const val SEARCH_PAGE_LIMIT = 50

    //Network call parameters
    const val HEADER_AUTHORIZATION = "Authorization"
    const val HEADER_USER_AGENT = "User-Agent"
    const val HEADER_CONTENT_ADVANCED_JSON = "Content-Type: application/vnd.api+json"
    const val HEADER_CONTENT_FORM_DATA = "Content-Type: multipart/form-data"

    const val HEADER_ACCEPT_LANGUAGE = "Accept-Language"
    const val HEADER_ACCEPT_ALL = "Accept: */*"
    const val HEADER_LANGUAGE = "Language"

    const val BEARER_PREFIX = "Bearer "
    const val LOCALE = "locale"
    const val WEBSITE_API = "{" + LOCALE + "}/api"
    const val MOBILE_API_V2 = "{$LOCALE}/mobileapi"
    const val MOBILE_API_V3 = "/mobileapi/v3"
    const val MANAGER_API = "manager/manager-api"
    const val MANAGER_FRONTEND_API = "manager/frontend"
    const val SEARCH_FORM = "/search-form"
    const val AUTOCOMPLETE_SEARCH_LOCATION = "/autocomplete/location"
    const val SEARCH_LOCATION = "/location"
    const val SEARCH = "/search"
    const val USER_SAVED_PROPERTIES = "/api/user/saved-property"


    //Parameters for the search location
    const val PARAM_LOCATION_FIELD = "fields[location]"
    const val PARAM_LOCATION_VALUE = "name,path_name"
    const val PARAM_NAME_FIELD = "filter[name]"
    const val PARAM_PATH_FIELD = "filter[pathPrefix]"
    const val PARAM_TYPE_FIELD = "filter[types]"
    const val PARAM_SORT_FIELD = "sort"
    const val PARAM_SORT_VALUE = "location_type"
    const val PARAM_PROPERTY_ID = "propertyid"
    const val PARAM_ETAG = "etag"

    //Search properties query filters
    const val FILTER_CATEGORY_ID_KEY = "filter[category_id]"
    const val FILTER_PROPERTY_TYPE_IDS_KEY = "filter[property_type_id]"
    const val FILTER_LOCATIONS_IDS_KEY = "filter[locations_ids]"
    const val FILTER_AMENITIES_KEY = "filter[amenities]"
    const val FILTER_BEDROOMS = "filter[number_of_bedrooms]"
    const val FILTER_MIN_PRICE_KEY = "filter[min_price]"
    const val FILTER_MAX_PRICE_KEY = "filter[max_price]"
    const val FILTER_MIN_AREA_KEY = "filter[min_area]"
    const val FILTER_MAX_AREA_KEY = "filter[max_area]"
    const val FILTER_PRICE_TYPE_KEY = "filter[price_type]"
    const val FILTER_FURNISHED_KEY = "filter[furnished]"
    const val FILTER_PAGE_NUMBER_KEY = "page[number]"
    const val FILTER_PAGE_LIMIT_KEY = "page[limit]"
    const val FILTER_AGENT_ID_KEY = "filter[agent_id]"
    const val FILTER_BROKER_ID_KEY = "filter[broker_id]"
    const val SORT_LAT_KEY = "sort_params[coordinate][lat]"
    const val SORT_LONG_KEY = "sort_params[coordinate][lon]"
    const val FILTER_NEAR_BY_LON_KEY = "filter[nearby][lon]"
    const val FILTER_NEAR_BY_LAT_KEY = "filter[nearby][lat]"
    const val FILTER_NEAR_RADIUS_KEY = "filter[nearby][radius]"
    const val FILTER_POLYGON_LAT = "filter[polygon][%d][lat]"
    const val FILTER_POLYGON_LON = "filter[polygon][%d][lon]"
    const val FILTER_INCLUDE = "include"
    const val SEARCH_PROPERTIES_INCLUDE = "properties,properties.property_images,properties.location_tree,properties.property_type"

    //Search filters configuration
    const val FILTER_SETTINGS_CATEGORY_KEY = "c"
    const val FILTER_SETTINGS_PROPERTY_TYPE_KEY = "t"
    const val FILTER_SETTINGS_BEDROOMS = FILTER_BEDROOMS
    const val FILTER_SETTINGS_PRICE_FROM = "pf"
    const val FILTER_SETTINGS_PRICE_TO = "pt"
    const val FILTER_SETTINGS_AREA_FROM = "af"
    const val FILTER_SETTINGS_AREA_TO = "at"
    const val FILTER_SETTINGS_PRICE_TYPE = "rp"
    const val FILTER_SETTINGS_LOCATION_IDS = "l"
    const val FILTER_SETTINGS_QUERY = "q"
    const val FILTER_SETTINGS_KEYWORDS = "kw"
    const val FILTER_SETTINGS_AMENITIES = "am[]"
    const val FILTER_SETTINGS_FURNISHED = "fu"
    const val FILTER_SETTINGS_SORT = "ob"

}