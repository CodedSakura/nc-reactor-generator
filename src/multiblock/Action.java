package multiblock;
import java.util.ArrayList;
public abstract class Action<T extends Multiblock>{
    public ActionResult<T> apply(T multiblock){
        ArrayList<Block> blocks = new ArrayList<>();
        getAffectedBlocks(multiblock, blocks);
        if(blocks.isEmpty())blocks = null;
        doApply(multiblock);
        if(blocks!=null){
            getAffectedBlocks(multiblock, blocks);
            if(blocks.isEmpty())blocks = null;
        }
        return new ActionResult<>(multiblock, blocks);
    }
    protected abstract void doApply(T multiblock);
    public ActionResult<T> undo(T multiblock){
        ArrayList<Block> blocks = new ArrayList<>();
        getAffectedBlocks(multiblock, blocks);
        if(blocks.isEmpty())blocks = null;
        doUndo(multiblock);
        if(blocks!=null){
            getAffectedBlocks(multiblock, blocks);
            if(blocks.isEmpty())blocks = null;
        }
        return new ActionResult<>(multiblock, blocks);
    }
    protected abstract void doUndo(T multiblock);
    protected abstract void getAffectedBlocks(T multiblock, ArrayList<Block> blocks);
}