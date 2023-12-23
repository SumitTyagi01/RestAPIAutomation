package resources;

import pojo.DeleteAPIPayload;
import pojo.Location;
import pojo.SerializePojo;

import java.util.ArrayList;
import java.util.List;

public class TestDataBuild_qa extends TestDataBuild {

    public SerializePojo addPlacePayload(String name,String address, String language){
        SerializePojo p = new SerializePojo();
        p.setAccuracy(50);
        p.setAddress(address);
        p.setLanguage(language);
        p.setName(name);
        p.setPhone_number("(+91) 983 893 3937");
        p.setWebsite("http://thisIsFromQA.com");

        List<String> ty = new ArrayList<String>();
        ty.add("shoe park");
        ty.add("shop");
        p.setTypes(ty);
        Location l = new Location();
        l.setLat(-38.383494);
        l.setLng(33.427362);
        p.setLocation(l);

        return p;
    }

    public DeleteAPIPayload deletePlacePayload(String placeId){
        DeleteAPIPayload del = new DeleteAPIPayload();
        del.setPlace_id(placeId);
        return del;
    }
}
