package tabby.song

import groovyx.javafx.beans.FXBindable
import javafx.scene.canvas.GraphicsContext
import javafx.scene.paint.Color

/**
 * Created by susanne on 26.07.16.
 */
class SongCanvas extends ResizableCanvas {

    @FXBindable
    Double initWidth = 2100d    // width of the paper without border
    @FXBindable
    Double initHeight = 2970d   // height of the paper without border
    @FXBindable
    Double borderSize = 50
    @FXBindable
    String paperSize = 'A4'     // e.g. 'a4', 'letter' etc.
    @FXBindable
    Double zoom = 1.0           // current zoom-ratio
    @FXBindable
    Boolean orientation = true  // true = portrait, false = landscape

    static def paperSizes = [
            'a4'    : [w: 2100d, h: 2970d],
            'a5'    : [w: 1480d, h: 2100d],
            'letter': [w: 2100d, h: 2794d],
            'legal' : [w: 2100d, h: 3556d]
    ]

    static def PORTRAIT = true
    static def LANDSCAPE = false

    void zoom(double newZoom) {
        zoom = newZoom
        zoomDraw()
    }

    void zoomDraw() {
        resize((initWidth + 2 * borderSize) * zoom, (initHeight + 2 * borderSize) * zoom)
        graphicsContext2D.scale(zoom, zoom)
        draw()
        double restoreZoom = 1 / zoom
        graphicsContext2D.scale(restoreZoom, restoreZoom)
    }

    @Override
    void draw() {
        GraphicsContext gc = graphicsContext2D
        gc.clearRect(0, 0, initWidth + 2 * borderSize, initHeight + 2 * borderSize)

        gc.fill = Color.WHITE
        gc.fillRect(borderSize, borderSize, initWidth, initHeight)

        gc.stroke = Color.RED
        gc.strokeLine(borderSize, borderSize, initWidth + borderSize, initHeight + borderSize)
        gc.strokeLine(borderSize, initHeight + borderSize, initWidth + borderSize, borderSize)
    }

    @Override
    void resize(double nwidth, double nheight) {
        setWidth(nwidth)
        setHeight(nheight)
    }

    void setInitHeight(double nheight) {
        initHeightProperty().set(nheight)
        setHeight(initHeight + 2 * borderSize)
    }

    void setInitWidth(double nwidth) {
        initWidthProperty().set(nwidth)
        setWidth(initWidth + 2 * borderSize)
    }

    void setBorderSize(double bSize) {
        borderSizeProperty().set(bSize)
        setWidth(initWidth + 2 * borderSize)
        setHeight(initHeight + 2 * borderSize)
        zoomDraw()
    }

    void setPaperSize(String pSize) {
        if (pSize in paperSizes) {
            paperSizeProperty().set(pSize)
            initWidth = paperSizes[pSize.toLowerCase()]['w']
            initHeight = paperSizes[pSize.toLowerCase()]['h']
            zoomDraw()
        }
    }

    void setOrientation(Boolean orient) {
        if (orientation != orient) {
            (initWidth, initHeight) = [initHeight, initWidth]
            orientationProperty().set(orient)
            zoomDraw()
        }
    }

}
