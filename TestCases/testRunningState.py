#{{{ Marathon
from default import *
#}}} Marathon

def test():

    set_java_recorded_version("15.0.1")
    if window('SVV WEB SERVER'):
        click('Start server')
        click('lbl:RUNNING')
        click('lbl:169.254.72.243')
        click('lbl:8080')
        rightclick('lbl:RUNNING')
        assert_p('lbl:RUNNING', 'Text', 'RUNNING')
        assert_p('lbl:169.254.72.243', 'Text', '169.254.72.243')
        assert_p('lbl:8080', 'Text', '8080')
        assert_p('Switch to maintenance mode', 'Enabled', 'true')
        select('Switch to maintenance mode', 'false')
        assert_p('Server listening on port', 'Text', '8080')
        assert_p('Server listening on port', 'Enabled', 'true')
        assert_p('JTextField_1', 'Enabled', 'true')
        assert_p('Maintenance directory', 'Enabled', 'true')
        assert_p('Browse', 'Enabled', 'false')
        assert_p('Browse_2', 'Enabled', 'false')
        assert_p('Apply', 'Enabled', 'false')
    close()

    pass