#{{{ Marathon
from default import *
#}}} Marathon

def test():

    set_java_recorded_version("15.0.1")
    if window('SVV WEB SERVER'):
        click('Start server')
        assert_p('lbl:STOPPED', 'Text', 'MAINTENANCE')
    close()

    pass