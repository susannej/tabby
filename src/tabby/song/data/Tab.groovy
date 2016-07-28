package tabby.song.data

/**
 * Created by susanne on 26.07.16.
 */
class Tab {

    NoteTab parent

    Integer fret
    Integer string
    def tabMod = [:] // bend, slide, hammering-on, pull-of
}
