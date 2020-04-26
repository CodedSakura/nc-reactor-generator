package overhaul;
import common.Setting;
import common.SettingBoolean;
import common.SettingDouble;
import common.ThingWithSettings;
import java.util.ArrayList;
import java.util.Random;
public abstract class GenerationModel extends ThingWithSettings{
    public static final ArrayList<GenerationModel> models = new ArrayList<>();
    public static final GenerationModel DEFAULT;
    static{
        models.add(new GenerationModel("Random", "Generates completely random reactors") {
            @Override
            public Reactor generate(Reactor last, Fuel fuel, Fuel.Type type, int x, int y, int z, Random rand){
                return Reactor.random(fuel,type,x,y,z,rand);
            }
        });
        models.add(new GenerationModel("Standard", "Generates random reactors until a valid reactor is found, then changes some random parts of the reactor to random other parts- if the result is better, keep the changes. if not, discard.", new SettingDouble("Change Chance", 1, 0.1, 100, .1), new SettingBoolean("Lock Core", false)){
            @Override
            public Reactor generate(Reactor last, Fuel fuel, Fuel.Type type, int x, int y, int z, Random rand){
                if(last!=null&&last.isValid()){
                    return new Reactor(fuel, type, x, y, z){
                        @Override
                        protected ReactorPart build(int X, int Y, int Z){
                            ArrayList<ReactorPart> allowedParts = ReactorPart.getSelectedParts();
                            if(getBoolean("Lock Core")){
                                if(ReactorPart.GROUP_ALL_CORE.contains(last.parts[X][Y][Z])){
                                    return last.parts[X][Y][Z];
                                }
                                for(ReactorPart part : ReactorPart.GROUP_ALL_CORE){
                                    allowedParts.remove(part);
                                }
                            }
                            if(rand.nextDouble()<getDouble("Change Chance")/100d){
                                return ReactorPart.random(rand, allowedParts);
                            }else{
                                return last.parts[X][Y][Z];
                            }
                        }
                    };
                }
                return Reactor.random(fuel,type,x,y,z,rand);
            }
        });
        DEFAULT = get("Standard");
    }
    public static GenerationModel get(String name){
        for(GenerationModel model : models){
            if(model.name.equalsIgnoreCase(name)){
                return model;
            }
        }
        return null;
    }
    public final String name;
    public final String description;
    public GenerationModel(String name, String description, Setting... settings){
        super(settings);
        this.name = name;
        this.description = description;
    }
    @Override
    public String toString(){
        return "Generation model: "+name;
    }
    public abstract Reactor generate(Reactor last, Fuel fuel, Fuel.Type type, int x, int y, int z, Random rand);
}