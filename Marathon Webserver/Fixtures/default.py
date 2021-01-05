'''Launcher uses the properties specified here to launch the application'''

#{{{ Fixture Properties
Fixture_properties = {
        'marathon.project.launcher.model' : 'net.sourceforge.marathon.runtime.RuntimeLauncherModel',
        'marathon.application.mainclass' : 'ui.ServerGUI',
        'marathon.application.arguments' : '',
        'marathon.application.vm.arguments' : '',
        'marathon.application.vm.command' : 'C:\\Program Files\\Java\\jdk-15.0.1\\bin\\java.exe',
        'marathon.application.working.dir' : '',
        'marathon.application.classpath' : '%marathon.project.dir%\\..\\eclipse-workspace\\WebServer\\bin'
    }
#}}} Fixture Properties

'''Default Fixture'''
class Fixture:


    def teardown(self):
        '''Marathon executes this method at the end of test script.'''
        pass

    def setup(self):
        '''Marathon executes this method before the test script. The application needs to be
        started here. You can add other tasks before start_application.'''
        pass

    def test_setup(self):
        '''Marathon executes this method after the first window of the application is displayed.
        You can add any Marathon script elements here.'''
        pass

fixture = Fixture()
