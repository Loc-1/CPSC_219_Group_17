import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(Arquillian.class)
public class BoardTest {
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addClass(Board.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Test
    public void isValidMove() throws Exception {
    }

    @Test
    public void getBoard() throws Exception {
    }

    @Test
    public void setBoard() throws Exception {
    }

    @Test
    public void setDifficulty() throws Exception {
    }

    @Test
    public void getRows() throws Exception {
    }

    @Test
    public void getColumns() throws Exception {
    }

}
