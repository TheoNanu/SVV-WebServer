#{{{ Marathon
from default import *
import time
#}}} Marathon

def test():

    set_java_recorded_version("15.0.1")
    if window('SVV WEB SERVER'):
        click('Start server')
        assert_p('lbl:STOPPED', 'Text', 'STOPPED')
        assert_p('STOPPED', 'Text', 'STOPPED')
        assert_p('lbl:STOPPED_2', 'Text', 'STOPPED')
        assert_p('Start server', 'Text', 'Start server')
        assert_p('Switch to maintenance mode', 'Enabled', 'false')
        assert_p('Server listening on port', 'Enabled', 'true')
        assert_p('JTextField_1', 'Enabled', 'true')
        assert_p('Maintenance directory', 'Enabled', 'true')
        assert_p('Browse', 'Text', 'Browse')
        assert_p('Browse', 'Enabled', 'false')
        assert_p('Browse_2', 'Enabled', 'false')
        assert_p('Apply', 'Enabled', 'false')
    close()


    pass