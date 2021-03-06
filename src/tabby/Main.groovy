package tabby

import javafx.geometry.Orientation
import tabby.song.SongCanvas
import tabby.song.SongData

/**
 * Created by susanne on 27.07.16.
 */

import static groovyx.javafx.GroovyFX.start

Configuration config = Configuration.instance
SongData song = new SongData(dirty: false)

start {

    registerBeanFactory "songCanvas", SongCanvas

    final fileChooser = fileChooser(initialDirectory: ".", title: "Songfile") {
        filter("All-files", extensions: ["*.*"])
        filter("TAB-files", extensions: ["*.gpx", "*.gp5", "*.gp4", "*.tabby"])
    }

    actions {
        fxaction(id: 'exitAction',
                name: 'Exit',
                onAction: { primaryStage.close() })
        fxaction(id: 'cutAction',
                name: 'Cut',
                //icon: 'icons/cut.png',
                onAction: { println "Cut" })
        fxaction(id: 'copyAction',
                name: 'Copy',
                //icon: 'icons/copy.png',
                onAction: { println "Copy" })
        fxaction(id: 'pasteAction',
                name: 'Paste',
                //icon: 'icons/paste.png',
                onAction: { println "Paste" })
        fxaction(id: 'checkAction',
                name: 'Check',
                onAction: { println "Check" })

        fxaction(id: 'newFile', name: "New ...", icon: 'resources/icons/new.png', onAction: {})
        fxaction(id: 'openFile', name: "Open ...", icon: 'resources/icons/open.png', onAction: {
            def selectedFile = fileChooser.showOpenDialog(primaryStage)
            if (selectedFile) {
                song.read(selectedFile)
                songCanvas.data = song
                primaryStage.title = "Tabby ${config.version} - ${selectedFile}"
            }
        })
        fxaction(id: 'saveFile', name: "Save ...", icon: 'resources/icons/save.png', onAction: {
            if (song.filename)
                song.writer()
            else {
                def selectedFile = fileChooser.showSaveDialog(primaryStage)
                if (selectedFile) {
                    song.write(selectedFile)
                    primaryStage.title = "Tabby ${config.version} - ${selectedFile}"
                }
            }
        })
        fxaction(id: 'saveAsFile', name: "Save as ...", icon: 'resources/icons/save-as.png', onAction: {
            def selectedFile = fileChooser.showSaveDialog(primaryStage)
            if (selectedFile) {
                song.write(selectedFile)
                primaryStage.title = "Tabby ${config.version} - ${selectedFile}"
            }
        })

        fxaction(id: 'note1', icon: 'resources/icons/1.png', onAction: {})
        fxaction(id: 'note2', icon: 'resources/icons/2.png', onAction: {})
        fxaction(id: 'note4', icon: 'resources/icons/4.png', onAction: {})
        fxaction(id: 'note8', icon: 'resources/icons/8.png', onAction: {})
        fxaction(id: 'note16', icon: 'resources/icons/16.png', onAction: {})
        fxaction(id: 'note32', icon: 'resources/icons/32.png', onAction: {})
        fxaction(id: 'note64', icon: 'resources/icons/64.png', onAction: {})

        fxaction(id: 'noteDot', icon: 'resources/icons/dotted.png', onAction: {})
        fxaction(id: 'noteDoubleDot', icon: 'resources/icons/doubledotted.png', onAction: {})
        fxaction(id: 'effectBend', icon: 'resources/icons/effect_bend.png', onAction: {})
        fxaction(id: 'effectDead', icon: 'resources/icons/effect_dead.png', onAction: {})
        fxaction(id: 'effectGhost', icon: 'resources/icons/effect_ghost.png', onAction: {})
        fxaction(id: 'effectHammer', icon: 'resources/icons/effect_hammer.png', onAction: {})
        fxaction(id: 'effectSlide', icon: 'resources/icons/effect_slide.png', onAction: {})
        fxaction(id: 'effectStaccato', icon: 'resources/icons/effect_staccato.png', onAction: {})
        fxaction(id: 'openRepeat', icon: 'resources/icons/openrepeat.png', onAction: {})
        fxaction(id: 'closeRepeat', icon: 'resources/icons/closerepeat.png', onAction: {})

        fxaction(id: 'zoom25', name: '25%', onAction: { songCanvas.zoom(0.25) })
        fxaction(id: 'zoom50', name: '50%', onAction: { songCanvas.zoom(0.5) })
        fxaction(id: 'zoom75', name: '75%', onAction: { songCanvas.zoom(0.75) })
        fxaction(id: 'zoom100', name: '100%', onAction: { songCanvas.zoom(1.0) })
        fxaction(id: 'zoom125', name: '125%', onAction: { songCanvas.zoom(1.25) })
        fxaction(id: 'zoom150', name: '150%', onAction: { songCanvas.zoom(1.5) })

        fxaction(id: 'portrait', name: 'Portrait', onAction: { songCanvas.orientation = SongCanvas.PORTRAIT })
        fxaction(id: 'landscape', name: 'Landscape', onAction: { songCanvas.orientation = SongCanvas.LANDSCAPE })
    }

    stage(title: "Tabby ${config.version}", width: 1024, height: 768, visible: true) {
        scene(/*stylesheets: 'theme/dark.css',*/ fill: GROOVYBLUE) {
            borderPane {
                top {
                    vbox {
                        menuBar {
                            menu("File") {
                                menuItem(newFile)
                                menuItem(openFile)
                                menuItem(saveFile)
                                saveAs = menuItem(saveAsFile)
                                separatorMenuItem()
                                menuItem(exitAction)
                            }
                            menu(text: "Edit") {
                                menuItem(cutAction)
                                menuItem(copyAction)
                                menuItem(pasteAction)
                                separatorMenuItem()
                                checkMenuItem(checkAction)
                                radioMenuItem("Radio", selected: true)
                                menu("Foo") {
                                    menuItem("Bar")
                                    menuItem("FooBar")
                                }
                            }
                        }
                        toolBar {
                            button(newFile, text: "")
                            button(openFile, text: "")
                            button(saveFile, text: "")
                            separator(orientation: Orientation.VERTICAL)
                            toggleButton(zoom25, id: 'zoom25Btn', toggleGroup: 'zoom')
                            toggleButton(zoom50, toggleGroup: 'zoom')
                            toggleButton(zoom75, toggleGroup: 'zoom')
                            toggleButton(zoom100, toggleGroup: 'zoom')
                            toggleButton(zoom125, toggleGroup: 'zoom')
                            toggleButton(zoom150, toggleGroup: 'zoom')
                            separator(orientation: Orientation.VERTICAL)
                            toggleButton(portrait, id: 'portraitBtn', toggleGroup: 'orient')
                            toggleButton(landscape, toggleGroup: 'orient')
                        }
                    }
                }
                left {
                    vbox(prefWidth: 170.0) {
                        flowPane {
                            toggleButton(note1, toggleGroup: 'note')
                            toggleButton(note2, toggleGroup: 'note')
                            toggleButton(note4, toggleGroup: 'note')
                            toggleButton(note8, toggleGroup: 'note')
                            toggleButton(note16, toggleGroup: 'note')
                            toggleButton(note32, toggleGroup: 'note')
                            toggleButton(note64, toggleGroup: 'note')
                        }
                        separator(prefHeight: 20)
                        flowPane {
                            toggleButton(noteDot, toggleGroup: 'dot')
                            toggleButton(noteDoubleDot, toggleGroup: 'dot')
                            toggleButton(openRepeat, toggleGroup: 'repeat')
                            toggleButton(closeRepeat, toggleGroup: 'repeat')
                            toggleButton(effectBend)
                            toggleButton(effectDead)
                            toggleButton(effectGhost)
                            toggleButton(effectHammer)
                            toggleButton(effectSlide)
                            toggleButton(effectStaccato)
                        }
                    }
                }
                center {
                    scrollPane {
                        songCanvas(id: 'songCanvas', paperSize: 'A4',/*width: 2200, height: 3070,*/ borderSize: 50, pageBorder: 20)
                    }
                }
            }
        }
    }
    zoom25Btn.fire()
    portraitBtn.fire()
}

