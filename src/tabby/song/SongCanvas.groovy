package tabby.song

import groovyx.javafx.beans.FXBindable
import javafx.scene.canvas.GraphicsContext
import javafx.scene.paint.Color

/**
 * Created by susanne on 26.07.16.
 */
class SongCanvas extends ResizableCanvas {

    @FXBindable
    Double initWidth
    @FXBindable
    Double initHeight
    @FXBindable
    Double borderSize

    void zoom(double zoom) {
        resize(initWidth * zoom, initHeight * zoom)
        graphicsContext2D.scale(zoom, zoom)
        draw()
        double restoreZoom = 1 / zoom
        graphicsContext2D.scale(restoreZoom, restoreZoom)
    }

    @Override
    void draw() {
        GraphicsContext gc = graphicsContext2D
        gc.clearRect(0, 0, initWidth, initHeight)

        gc.fill = Color.WHITE
        gc.fillRect(borderSize, borderSize, initWidth - 2 * borderSize, initHeight - 2 * borderSize)

        gc.stroke = Color.RED
        gc.strokeLine(borderSize, borderSize, initWidth - borderSize, initHeight - borderSize)
        gc.strokeLine(borderSize, initHeight - borderSize, initWidth - borderSize, borderSize)
    }

    @Override
    void resize(double nwidth, double nheight) {
        setWidth(nwidth)
        setHeight(nheight)
        if (initWidth == 0.0d) setInitWidth(nwidth)
        if (initHeight == 0.0d) setInitHeight(nheight)
    }

}
