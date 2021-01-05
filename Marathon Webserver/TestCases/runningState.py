#{{{ Marathon
from default import *
#}}} Marathon

def test():

    set_java_recorded_version("15.0.1")
    if window('SVV WEB SERVER'):
        click('Start server')
        assert_p('lbl:STOPPED', 'Text', 'RUNNING')
        assert_p('STOPPED', 'Text', '169.254.72.243')
        assert_p('Server listening on port', 'Text', '8081')
        assert_p('Stop server', 'Text', 'Stop server')
        assert_p('Switch to maintenance mode', 'Enabled', 'true')
        select('Switch to maintenance mode', 'false')
        assert_p('Server listening on port_2', 'Enabled', 'true')
        assert_p('JTextField_1', 'Enabled', 'true')
        assert_p('Maintenance directory', 'Enabled', 'true')
        assert_p('Browse', 'Enabled', 'false')
        assert_p('Browse_2', 'Enabled', 'false')
        assert_p('Apply', 'Enabled', 'false')
    close()

    pass