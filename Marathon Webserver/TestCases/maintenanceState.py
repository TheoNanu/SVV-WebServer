#{{{ Marathon
from default import *
#}}} Marathon

def test():

    set_java_recorded_version("15.0.1")
    if window('SVV WEB SERVER'):
        click('Start server')
        select('Switch to maintenance mode', 'true')
        assert_p('lbl:STOPPED', 'Text', 'MAINTENANCE')
        select('Switch to maintenance mode', 'true')
        assert_p('STOPPED', 'Text', '169.254.72.243')
        assert_p('Server listening on port', 'Text', '8081')
        assert_p('Stop server', 'Text', 'Stop server')
        assert_p('Server listening on port_2', 'Enabled', 'true')
        assert_p('JTextField_1', 'Enabled', 'true')
        assert_p('Maintenance directory', 'Enabled', 'true')
        assert_p('Browse', 'Enabled', 'true')
        assert_p('Browse_2', 'Enabled', 'true')
        assert_p('Apply', 'Enabled', 'true')
    close()

    pass