package tabby.song

import com.sun.deploy.xml.XMLNode

/**
 * Created by susanne on 01.08.16.
 */
class SongData {

    String filename
    Boolean dirty
    def xml

    def read(File file) {
        xml = new XmlParser().parse(file.path)
        dirty = false
    }

    def write(File nfile = null) {
        if (nfile) filename = nfile.path

        def writer = new FileWriter(filename)
        def printer = new XmlNodePrinter(new PrintWriter(writer))
        printer.preserveWhitespace = true
        printer.print(xml)
        dirty = false
    }

}
