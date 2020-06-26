package planner.menu.component;
import java.awt.Color;
import planner.Core;
import planner.multiblock.Block;
import planner.multiblock.Multiblock;
import simplelibrary.opengl.gui.components.MenuComponent;
public class MenuComponentVisibleBlock extends MenuComponent{
    public final Multiblock multiblock;
    public final int blockX;
    public final int blockY;
    public final int blockZ;
    public MenuComponentVisibleBlock(int x, int y, int width, int height, Multiblock multiblock, int blockX, int blockY, int blockZ){
        super(x, y, width, height);
        float tint = .9f;
        this.multiblock = multiblock;
        color = Core.theme.getButtonColor(tint);
        foregroundColor = Core.theme.getTextColor();
        this.blockX = blockX;
        this.blockY = blockY;
        this.blockZ = blockZ;
    }
    @Override
    public void render(){
        Color col = new Color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
        Core.applyColor(col);
        drawRect(x, y, x+width, y+height, 0);
        double border = height/8;
        Core.applyColor(foregroundColor);
        drawRect(x, y, x+width, y+border/4, 0);
        drawRect(x, y+height-border/4, x+width, y+height, 0);
        drawRect(x, y+border/4, x+border/4, y+height-border/4, 0);
        drawRect(x+width-border/4, y+border/4, x+width, y+height-border/4, 0);
        Core.applyColor(foregroundColor);
        drawText();
        Core.applyColor(color);
    }
    public void drawText(){
        Block block = multiblock.getBlock(blockX, blockY, blockZ);
        if(block==null)return;
        if(block.getTexture()==null){
            drawCenteredText(x, y+height*.4, x+width, y+height*.6, block.getName());
        }else{
            Core.applyWhite();
            drawRect(x, y, x+width, y+height, Core.getTexture(block.getTexture()));
        }
    }
    @Override
    public boolean mouseWheelChange(int wheelChange){
        return parent.mouseWheelChange(wheelChange);
    }
}