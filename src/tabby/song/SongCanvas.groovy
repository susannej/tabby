package tabby.song

import groovyx.javafx.beans.FXBindable
import javafx.geometry.Bounds
import javafx.scene.canvas.GraphicsContext
import javafx.scene.paint.Color
import javafx.scene.text.Font
import javafx.scene.text.FontWeight
import javafx.scene.text.Text
import javafx.scene.text.TextAlignment
import tabby.Configuration

/**
 * Created by susanne on 26.07.16.
 */
class SongCanvas extends ResizableCanvas {

    Configuration config = Configuration.instance

    @FXBindable
    Double initWidth = 2100d    // width of the paper without border
    @FXBindable
    Double initHeight = 2970d   // height of the paper without border
    @FXBindable
    Double borderSize = 50
    @FXBindable
    Double pageBorder = 20
    @FXBindable
    String paperSize = 'A4'     // e.g. 'a4', 'letter' etc.
    @FXBindable
    Double zoom = 1.0           // current zoom-ratio
    @FXBindable
    Boolean orientation = true  // true = portrait, false = landscape
    @FXBindable
    def data

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
        def orgWidth = initWidth + 2 * borderSize
        def orgHeight = initHeight + 2 * borderSize

        gc.clearRect(0, 0, orgWidth, orgHeight)

        gc.fill = Color.WHITE
        gc.fillRect(borderSize, borderSize, initWidth, initHeight)

        gc.stroke = Color.RED
        gc.strokeLine(borderSize, borderSize, initWidth + borderSize, initHeight + borderSize)
        gc.strokeLine(borderSize, initHeight + borderSize, initWidth + borderSize, borderSize)

        if (data) {
            Long actualLine
            gc.fill = Color.BLACK
            gc.setTextAlign(TextAlignment.CENTER)
            Font font

            // Title
            if (data.xml.title) {
                font = Font.font(config.titleFontName, FontWeight.BOLD, config.titleFontSize as Integer)
                gc.font = font
                actualLine = textBounds(data.xml.title.text(), font).height + borderSize + pageBorder
                gc.fillText(data.xml.title.text(), orgWidth / 2, actualLine, orgWidth - 2 * (borderSize + pageBorder))
            }
            // Subtitle
            if (data.xml.subtitle) {
                font = Font.font(config.subtitleFontName, FontWeight.BOLD, config.subtitleFontSize as Integer)
                gc.font = font
                actualLine += textBounds(data.xml.subtitle.text(), font).height
                gc.fillText(data.xml.subtitle.text(), orgWidth / 2, actualLine, orgWidth - 2 * (borderSize + pageBorder))
            }
            font = Font.font(config.defaultFontName, FontWeight.BOLD, config.defaultFontSize as Integer)
            gc.font = font
            def fontHeight = textBounds(data.xml.words.text(), font).height
            // Artist
            if (data.xml.artist) {
                actualLine += fontHeight
                gc.fillText(data.xml.artist.text(), orgWidth / 2, actualLine, orgWidth - 2 * (borderSize + pageBorder))
            }
            // Album
            if (data.xml.album) {
                actualLine += fontHeight
                gc.fillText(data.xml.album.text(), orgWidth / 2, actualLine, orgWidth - 2 * (borderSize + pageBorder))
            }
            // Words by
            // Music by
            if (data.xml.words || data.xml.music) {
                actualLine += fontHeight
                if (data.xml.words) {
                    gc.setTextAlign(TextAlignment.LEFT)
                    gc.fillText("Words by " + data.xml.words.text(), borderSize + pageBorder, actualLine, (orgWidth - 2 * (borderSize + pageBorder)) / 2)
                }
                if (data.xml.music) {
                    gc.setTextAlign(TextAlignment.RIGHT)
                    gc.fillText("Music by " + data.xml.music.text(), orgWidth - borderSize - pageBorder, actualLine, (orgWidth - 2 * (borderSize + pageBorder)) / 2)
                }
            }
            // empty line
            actualLine += fontHeight
            actualLine += fontHeight
            gc.setTextAlign(TextAlignment.LEFT)
            gc.fillText("Standard tuning", borderSize + pageBorder, actualLine, (orgWidth - 2 * (borderSize + pageBorder)) / 2)
            actualLine += fontHeight
            if (data.xml.tempo) {
                actualLine += fontHeight
                gc.fillText(data.xml.tempo.text() + " Bpm", borderSize + pageBorder, actualLine, (orgWidth - 2 * (borderSize + pageBorder)) / 2)
            }
        }
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

    void setData(def xml) {
        dataProperty().set(xml)
        zoomDraw()
    }

    Bounds textBounds(String text, Font font) {
        def textNode = new Text(text)
        textNode.font = font
        textNode.layoutBounds
    }

}
