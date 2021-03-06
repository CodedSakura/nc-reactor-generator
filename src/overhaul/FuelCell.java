package overhaul;
public class FuelCell extends ReactorPart{
    /**
     * Not the efficiency of the cell, but the efficiency of the attached source.<br>
     * 0 if there is no attached source.
     */
    public float efficiency;
    public FuelCell(String name, String jsonName){
        super(Type.FUEL_CELL, "Fuel Cell"+(name==null?"":" ("+name+")"), jsonName, "fuel_cell/"+"fuel_cell"+(name==null?"":("_"+name.toLowerCase().replace(" ", "_"))));
    }
}