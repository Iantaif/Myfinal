package com.example.myapplication.Data;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import com.example.myapplication.Constant.Myconstants;
import com.example.myapplication.Database.RoomDB;
import com.example.myapplication.Models.Items;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AppData extends Application {

    RoomDB database;
    String category;
    Context context;
    public static final String LAST_VERSION="LAST_VERSION";
    public static final int NEW_VERSION = 3;

    public AppData(RoomDB database) {
        this.database = database;
    }

    public AppData(RoomDB database, Context context) {
        this.database = database;
        this.context = context;
    }

    public List<Items> getBasicData(){
        category = "Basic Needs";
        List<Items> basicItem = new ArrayList<>();
        basicItem.add(new Items("Visa",category,false));
        basicItem.add(new Items("Passport",category,false));
        basicItem.add(new Items("Tickets",category,false));
        basicItem.add(new Items("Wallet",category,false));
        basicItem.add(new Items("Driving License",category,false));
        basicItem.add(new Items("Currency",category,false));
        basicItem.add(new Items("House Key",category,false));
        basicItem.add(new Items("Book",category,false));
        basicItem.add(new Items("Travel Pillow",category,false));
        basicItem.add(new Items("Eye Patch",category,false));
        basicItem.add(new Items("Umbrella",category,false));
        basicItem.add(new Items("NoteBook",category,false));
  return basicItem;

    }


    public List<Items> getPersonalCareData(){
        String []data={"Tooth-brush","Tooth-paste","Mouthwash","Floss","Shaving cream","Razor blade",
        "Soap","Fiber","Shampoo","Hair Conditioner","Brush","Comb"};
        return prepareItemsList(Myconstants.PERSONAL_CARE_CAMEL_CASE,data);

    }
    public List<Items> getClothingData(){
        String []data={"Stockings","Underwear","Pajama","T-Shirts","Dress","Cardigan",
                "Vest","Jeans","Short","Suit","Coat","Belt"};
        return prepareItemsList(Myconstants.CLOTHING_CAMEL_CASE,data);
    }
    public List<Items> getBabyNeedsData(){
        String []data={"Snapsuit","Outfit","Baby Socks","Baby-Shirts","Baby Hat"," BaBy  Cardigan",
                " Baby Vest","Baby Jeans","Baby Short","BaBy Lotion","Baby Coat","BaBy Belt"};
        return prepareItemsList(Myconstants.BABY_NEEDS_CAMEL_CASE,data);

    }
    public List<Items> getHealthData(){
        String []data={"Pills","First aid kits","X-ray","band-aids"};
        return prepareItemsList(Myconstants.HEALTH_CAMEL_CASE,data);

    }
    public List<Items> getTechnologyData(){
        String []data={"Laptops","USB","Hard software","Phone"};
        return prepareItemsList(Myconstants.TECHNOLOGY_CAMEL_CASE,data);

    }
    public List<Items> getFoodData(){
        String []data={"Buger","Cake"," Drinks","Vegetables","Meat","Nut"};
        return prepareItemsList(Myconstants.FOOD_CAMEL_CASE,data);

    }
    public List<Items> getBeachSuppliesData(){
        String []data={"Snapsuit","Outfit","Baby Socks","Baby-Shirts","Baby Hat"," BaBy  Cardigan",
                " Baby Vest","Baby Jeans","Baby Short","BaBy Lotion","Baby Coat","BaBy Belt"};
        return prepareItemsList(Myconstants.BEACH_SUPPLIES_CAMEL_CASE,data);

    }
    public List<Items> getCarSuppliesData(){
        String []data={"Pillow","Drinks"};
        return prepareItemsList(Myconstants.CAR_SUPPLIES_CAMEL_CASE,data);

    }
    public List<Items> getNeedsData(){
        String []data={"Snapsuit","Outfit","Baby Socks","Baby-Shirts","Baby Hat"," BaBy  Cardigan",
                " Baby Vest","Baby Jeans","Baby Short","BaBy Lotion","Baby Coat","BaBy Belt"};
        return prepareItemsList(Myconstants.NEEDS_CAMEL_CASE,data);

    }

    public List<Items> prepareItemsList(String category,String[]data){
        List<String> list = Arrays.asList(data);
        List<Items> dataList = new ArrayList<>();
        dataList.clear();

        for(int i =0;i< list.size();i++){
            dataList.add(new Items(list.get(i),category,false ));
        }
        return dataList;
    }

    public List<List<Items>> getAllData(){
        List<List<Items>> listOfAllItems = new ArrayList<>();
        listOfAllItems.clear();
        listOfAllItems.add(getBasicData());
        listOfAllItems.add(getClothingData());
        listOfAllItems.add(getFoodData());
        listOfAllItems.add(getPersonalCareData());
        listOfAllItems.add(getBabyNeedsData());
        listOfAllItems.add(getHealthData());
        listOfAllItems.add(getTechnologyData());
        listOfAllItems.add(getNeedsData());
        listOfAllItems.add(getBeachSuppliesData());
        listOfAllItems.add(getCarSuppliesData());
        return  listOfAllItems;
    }
    public void persistAllData(){
        List<List<Items>> listOfAllItems = getAllData();
        for(List<Items> list:listOfAllItems){
            for(Items items:list){
                database.mainDao().saveItem(items);
            }
        }
        System.out.println(("Data added."));
    }
    public void persistdataByCategory(String category, Boolean onlyDelete){
        try {
            List<Items> list = deleteAndGetListByCategory(category,onlyDelete);
            if(!onlyDelete){
                for (Items item : list){
                    database.mainDao().saveItem(item);
                }
                Toast.makeText(context, category+"Reset Success", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(context, category+"Reset Success", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception ex){
            ex.printStackTrace();
            Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
        }
    }

    private List<Items> deleteAndGetListByCategory(String category,Boolean onlyDelete){
        if(onlyDelete){
            database.mainDao().deleteAllByCategoryAndAddedBy(category,Myconstants.SYSTEM_SMALL);
        }else {
            database.mainDao().deleteAllByCategory(category);
        }
        switch (category){
            case Myconstants.BASIC_NEEDS_CAMEL_CASE:
                return  getBasicData();
            case Myconstants.CLOTHING_CAMEL_CASE:
                return  getClothingData();
            case Myconstants.PERSONAL_CARE_CAMEL_CASE:
                return  getPersonalCareData();
            case Myconstants.BABY_NEEDS_CAMEL_CASE:
                return  getBabyNeedsData();
            case Myconstants.HEALTH_CAMEL_CASE:
                return  getHealthData();
            case Myconstants.TECHNOLOGY_CAMEL_CASE:
                return  getTechnologyData();
            case Myconstants.FOOD_CAMEL_CASE:
                return  getFoodData();
            case Myconstants.CAR_SUPPLIES_CAMEL_CASE:
                return  getCarSuppliesData();
            case Myconstants.NEEDS_CAMEL_CASE:
                return  getNeedsData();
            default:
                return new ArrayList<>();


        }
    }
}
