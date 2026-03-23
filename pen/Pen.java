package pen;

public class Pen {
    private String inkColor;
    private boolean opened;
    private WriteStrategy writer;
    private RefillStrategy refiller;
    private OpenCloseStrategy mechanism;

    public Pen(String inkColor, WriteStrategy writer, RefillStrategy refiller, OpenCloseStrategy mechanism) {
        this.inkColor = inkColor;
        this.writer = writer;
        this.refiller = refiller;
        this.mechanism = mechanism;
        this.opened = false;
    }

    public void start() {
        mechanism.open();
        opened = true;
    }

    public void close() {
        mechanism.close();
        opened = false;
    }

    public void write() throws Exception {
        if (!opened) {
            throw new Exception("Cannot write! The pen is closed. Please start() the pen first.");
        }
        System.out.print("[" + inkColor.toUpperCase() + "] ");
        writer.write();
    }

    public void refill(String newColor) {
        refiller.refill();
        this.inkColor = newColor;
        System.out.println("Pen has been refilled with " + newColor + " ink.");
    }
}
