package resourses;
// enum i s a special class that contain collection of constants and methods
public enum APIResourses {

    AddPlaceAPI("/maps/api/place/add/json"),
    getPlaceAPI("/maps/api/place/get/json"),
    DeletePlaceAPI("/maps/api/place/delete/json");
    private String resource;
    APIResourses(String resource) {
        this.resource = resource;
    }

    public String getResource(){
        return resource;
    }
}
