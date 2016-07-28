package tabby.song.data

/**
 * Created by susanne on 26.07.16.
 */
class Note {

    NoteTab parent

    Integer duration
    def durationMod = [:]   // dotted, double-dotted
    String tonePitch
    def noteMod = [:]       // dead, ghost, stacato
}
