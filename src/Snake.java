import java.util.ArrayList;

public class Snake {
    protected ArrayList<Block> body;
    protected boolean eaten = false;
    public Snake(Block first) {
        body = new ArrayList<>();
        body.add(first);
    }

    public void grow(){
        Block lastBlock = body.get(body.size()-1);
        int newX = lastBlock.x;
        int newY = lastBlock.y;
        body.add(new Block(lastBlock.x, lastBlock.y, lastBlock.WIDTH, lastBlock.HEIGHT));
        eaten = true;
    }
}
