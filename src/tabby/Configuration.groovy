package tabby
/**
 * Created by susanne on 21.07.16.
 */
@Singleton(lazy=true)
class Configuration {

    String version = '0.1'
    String titleFontName = 'Times New Roman'
    String titleFontSize = '96'
    String subtitleFontName = 'sanserif'
    String subtitleFontSize = '48'
    String defaultFontName = 'sanserif'
    String defaultFontSize = '24'

    void read() {

    }

    void write() {

    }
}
