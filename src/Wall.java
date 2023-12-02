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
            if (block.getColor().equals(color))
                return Optional.of(block);
            if (block instanceof CompositeBlock) {
                Optional<Block> nestedBlock = ((CompositeBlock) block).getBlocks()
                        .stream()
                        .filter(b -> b.getColor().equals(color))
                        .findFirst();
                if (nestedBlock.isPresent()) {
                    return nestedBlock;
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Block> findBlocksByMaterial(String material) {
        List<Block> blockList = new ArrayList<>();

        for (Block block : blocks) {
            if (block.getMaterial().equals(material))
                blockList.add(block);
            if (block instanceof CompositeBlock) {
                blockList.addAll(((CompositeBlock) block).getBlocks()
                        .stream()
                        .filter(b -> b.getMaterial().equals(material))
                        .toList());
            }
        }
        return blockList;
    }

    @Override
    public int count() {
        int counter = blocks.size();
        for (Block block : blocks) {
            if (block instanceof CompositeBlock) {
                counter += ((CompositeBlock) block).getBlocks().size() - 1;
            }
        }
        return counter;
    }
}
