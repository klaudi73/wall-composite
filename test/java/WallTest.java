import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class WallTest {

    @Test
    void findBlockByColor_ShouldReturnCorrectBlock_WhenColorExists() {
        Block block1 = new TestBlock("Red", "Brick");
        Block block2 = new TestBlock("Gray", "Concrete");
        Block block3 = new TestBlock("Brown", "Wood");
        Block block4 = new TestBlock("Lightgray", "Airbrick");
        Block block5 = new TestBlock("Silver", "Steel");
        Block block6 = new TestBlock("Brown", "Wood");

        List<Block> blocks = Arrays.asList(block1, block2, block3, block4, block5, block6);
        Wall wall = new Wall(blocks);

        Optional<Block> result = wall.findBlockByColor("Brown");

        assertTrue(result.isPresent());
        assertEquals("Brown", result.get().getColor());
        assertEquals("Wood", result.get().getMaterial());
    }


    @Test
    void findBlockByColor_ShouldReturnCorrectBlock_WhenColorExists_with_CompositeBlock() {
        Block block1 = new TestBlock("Red", "Brick");
        Block block2 = new TestBlock("Gray", "Concrete");
        Block block3 = new TestBlock("Brown", "Wood");
        Block block4 = new TestBlock("Lightgray", "Airbrick");
        Block block5 = new TestBlock("Silver", "Steel");
        Block block6 = new TestBlock("Brown", "Wood");
        CompositeBlock compositeBlock = new WallTest.TestCompositeBlock(Arrays.asList(block1, block2, block3));
        CompositeBlock compositeBlock2 = new WallTest.TestCompositeBlock(Arrays.asList(block4, block5, block6));
        List<Block> blocks = Arrays.asList(block4, compositeBlock, block5, compositeBlock2, block6);
        Wall wall = new Wall(blocks);

        Optional<Block> result = wall.findBlockByColor("Brown");

        assertTrue(result.isPresent());
        assertEquals("Brown", result.get().getColor());
        assertEquals("Wood", result.get().getMaterial());
    }

    @Test
    void findBlockByColor_ShouldReturnEmptyOptional_WhenColorDoesNotExist() {
        Block block1 = new TestBlock("Red", "Brick");
        Block block2 = new TestBlock("Gray", "Concrete");
        Block block3 = new TestBlock("Brown", "Wood");
        // Block block4 = new SimpleBlock("Lightgray", "Airbrick");
        Block block5 = new TestBlock("Silver", "Steel");
        Block block6 = new TestBlock("Brown", "Wood");

        List<Block> blocks = Arrays.asList(block1, block2, block3, block5, block6);
        Wall wall = new Wall(blocks);

        Optional<Block> result = wall.findBlockByColor("Lightgray");

        assertFalse(result.isPresent());
    }

    @Test
    void findBlockByColor_ShouldReturnEmptyOptional_WhenColorDoesNotExist_with_CompositeBlock() {
        Block block1 = new TestBlock("Red", "Brick");
        Block block2 = new TestBlock("Gray", "Concrete");
        Block block3 = new TestBlock("Brown", "Wood");
        Block block4 = new TestBlock("Lightgray", "Airbrick");
        Block block5 = new TestBlock("Silver", "Steel");
        Block block6 = new TestBlock("Brown", "Wood");
        CompositeBlock compositeBlock = new WallTest.TestCompositeBlock(
                Arrays.asList(block1, block2, block3, block4, block5, block6));

        List<Block> blocks = Arrays.asList(compositeBlock);
        Wall wall = new Wall(blocks);

        Optional<Block> result = wall.findBlockByColor("Yellow");

        assertFalse(result.isPresent());
    }

    @Test
    void findBlocksByMaterial_ShouldReturnCorrectBlocks_WhenMaterialExists() {
        Block block1 = new TestBlock("Red", "Brick");
        Block block2 = new TestBlock("Gray", "Concrete");
        Block block3 = new TestBlock("Brown", "Wood");
        Block block4 = new TestBlock("Lightgray", "Airbrick");
        Block block5 = new TestBlock("Silver", "Steel");
        Block block6 = new TestBlock("Brown", "Wood");

        List<Block> blocks = Arrays.asList(block1, block2, block3, block4, block5, block6);
        Wall wall = new Wall(blocks);

        List<Block> result = wall.findBlocksByMaterial("Wood");

        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(b -> b.getMaterial().equals("Wood")));
    }

    @Test
    void findBlocksByMaterial_ShouldReturnCorrectBlocks_WhenMaterialExists_with_CompositeBlock() {
        Block block1 = new TestBlock("Red", "Brick");
        Block block2 = new TestBlock("Gray", "Concrete");
        Block block3 = new TestBlock("Brown", "Wood");
        Block block4 = new TestBlock("Lightgray", "Airbrick");
        Block block5 = new TestBlock("Silver", "Steel");
        Block block6 = new TestBlock("Brown", "Wood");
        CompositeBlock compositeBlock = new WallTest.TestCompositeBlock(
                Arrays.asList(block1, block2, block3, block4, block5, block6));

        List<Block> blocks = Arrays.asList(compositeBlock);
        Wall wall = new Wall(blocks);

        List<Block> result = wall.findBlocksByMaterial("Brick");

        assertEquals(1, result.size());
        assertTrue(result.stream().allMatch(b -> b.getMaterial().equals("Brick")));
    }

    @Test
    void findBlocksByMaterial_ShouldReturnEmptyList_WhenMaterialDoesNotExist() {
        Block block1 = new TestBlock("Red", "Brick");
        Block block2 = new TestBlock("Gray", "Concrete");
        Block block3 = new TestBlock("Brown", "Wood");
        Block block4 = new TestBlock("Lightgray", "Airbrick");
        // Block block5 = new SimpleBlock("Silver", "Steel");
        Block block6 = new TestBlock("Brown", "Wood");

        List<Block> blocks = Arrays.asList(block1, block2, block3, block4, block6);
        Wall wall = new Wall(blocks);

        List<Block> result = wall.findBlocksByMaterial("Steel");

        assertTrue(result.isEmpty());
    }

    @Test
    void findBlocksByMaterial_ShouldReturnEmptyList_WhenMaterialDoesNotExist_with_CompositeBlock() {
        Block block1 = new TestBlock("Red", "Brick");
        Block block2 = new TestBlock("Gray", "Concrete");
        Block block3 = new TestBlock("Brown", "Wood");
        Block block4 = new TestBlock("Lightgray", "Airbrick");
        // Block block5 = new TestBlock("Silver", "Steel");
        Block block6 = new TestBlock("Brown", "Wood");
        CompositeBlock compositeBlock = new WallTest.TestCompositeBlock(
                Arrays.asList(block1, block2, block3, block4, block6));

        List<Block> blocks = Arrays.asList(compositeBlock);
        Wall wall = new Wall(blocks);

        List<Block> result = wall.findBlocksByMaterial("Steel");

        assertTrue(result.isEmpty());
    }

    @Test
    void count_ShouldReturnCorrectNumberOfBlocks() {
        Block block1 = new TestBlock("Red", "Brick");
        Block block2 = new TestBlock("Gray", "Concrete");
        Block block3 = new TestBlock("Brown", "Wood");
        Block block4 = new TestBlock("Lightgray", "Airbrick");
        Block block5 = new TestBlock("Silver", "Steel");
        Block block6 = new TestBlock("Brown", "Wood");

        List<Block> blocks = Arrays.asList(block1, block2, block3, block4, block5, block6);
        Wall wall = new Wall(blocks);

        int result = wall.count();

        assertEquals(6, result);
    }

    @Test
    void count_ShouldReturnCorrectNumberOfBlocks_with_CompositeBlock() {
        Block block1 = new TestBlock("Red", "Brick");
        Block block2 = new TestBlock("Gray", "Concrete");
        Block block3 = new TestBlock("Brown", "Wood");
        Block block4 = new TestBlock("Lightgray", "Airbrick");
        Block block5 = new TestBlock("Silver", "Steel");
        Block block6 = new TestBlock("Brown", "Wood");
        CompositeBlock compositeBlock = new WallTest.TestCompositeBlock(
                Arrays.asList(block1, block2, block3, block4, block5, block6));
        CompositeBlock compositeBlock2 = new WallTest.TestCompositeBlock(
                Arrays.asList(block6, block5, block3));
        List<Block> blocks = Arrays.asList(block1, compositeBlock, block2,  compositeBlock2, block4);
        Wall wall = new Wall(blocks);

        int result = wall.count();

        assertEquals(12, result);
    }


    private static class TestBlock implements Block {
        private final String color;
        private final String material;

        public TestBlock(String color, String material) {
            this.color = color;
            this.material = material;
        }

        @Override
        public String getColor() {
            return color;
        }

        @Override
        public String getMaterial() {
            return material;
        }
    }

    private static class TestCompositeBlock implements CompositeBlock {
        private final List<Block> blocks;

        public TestCompositeBlock(List<Block> blocks) {
            this.blocks = blocks;
        }

        @Override
        public List<Block> getBlocks() {
            return blocks;
        }

        @Override
        public String getColor() {
            // Implement if necessary
            return null;
        }

        @Override
        public String getMaterial() {
            // Implement if necessary
            return null;
        }
    }
}
