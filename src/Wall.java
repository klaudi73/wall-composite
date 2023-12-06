import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Wall implements Structure {
    private List<Block> blocks;

    public Wall(List<Block> blocks) {
        this.blocks = blocks;
    }

    @Override
    public Optional<Block> findBlockByColor(String color) {
        for (Block block : blocks) {
            Optional<Block> result =
                    findBlockByColorRecursive(block, color);
            if (result.isPresent()) return result;
        }
        return Optional.empty();
    }

    private Optional<Block> findBlockByColorRecursive(Block block, String color) {
        if (color.equals(block.getColor())) {
            return Optional.of(block);
        }

        if (block instanceof CompositeBlock) {
            CompositeBlock compositeBlock = (CompositeBlock) block;
            for (Block subBlock : compositeBlock.getBlocks()) {
                Optional<Block> result = findBlockByColorRecursive(subBlock, color);
                if (result.isPresent()) {
                    return result;
                }
            }
        }

        return Optional.empty();
    }


    @Override
    public List<Block> findBlocksByMaterial(String material) {
        List<Block> blockList = new ArrayList<>();

        for (Block block : blocks) {
            findBlocksByMaterialRecursive(block, material, blockList);
        }
        return blockList;
    }

    private void findBlocksByMaterialRecursive(Block block, String material, List<Block> result) {
        if (material.equals(block.getMaterial())) {
            result.add(block);
        }

        if (block instanceof CompositeBlock) {
            CompositeBlock compositeBlock = (CompositeBlock) block;
            for (Block subBlock : compositeBlock.getBlocks()) {
                findBlocksByMaterialRecursive(subBlock, material, result);
            }
        }
    }

    @Override
    public int count() {
        return countRecursive(blocks);
    }

    private int countRecursive(List<Block> blocks) {
        int count = blocks.size();
        for (Block block : blocks) {
            if (block instanceof CompositeBlock) {
                CompositeBlock compositeBlock = (CompositeBlock) block;
                count += countRecursive(compositeBlock.getBlocks()) - 1;
            }
        }
        return count;
    }
}
