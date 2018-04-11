import javafx.event.Event;
import javafx.event.EventType;

public class BoardObserver extends Observer {
    public BoardObserver(Board board) {
        this.board = board;
        this.board.attach(this);
    }

    @Override
    public void update() {
        new BoardEvent();
    }

    static class BoardEvent extends Event {
        private static final EventType<BoardEvent> BOARD_EVENT = new EventType<>(Event.ANY, "BOARD_EVENT");

        public BoardEvent() {
            super(BOARD_EVENT);
        }

    }
}
