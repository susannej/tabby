package tabby.song

import javafx.scene.canvas.Canvas
import javafx.scene.canvas.GraphicsContext
import javafx.scene.paint.Color

/**
 * Created by susanne on 28.07.16.
 */
class ResizableCanvas extends Canvas {

    void draw() {
        GraphicsContext gc = graphicsContext2D
        gc.clearRect(0, 0, width, height)

        gc.stroke = Color.RED
        gc.strokeLine(0, 0, width, height)
        gc.strokeLine(0, height, width, 0)
    }

    @Override
    boolean isResizable() {
        true
    }

    @Override
    void resize(double nwidth, double nheight) {
        println "resize !!!"
        setWidth(nwidth)
        setHeight(nheight)
        draw()
    }

}
