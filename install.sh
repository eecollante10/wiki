cp -R dist/ /usr/local/share/wiki
echo '#!/bin/bash' > wiki
echo 'java -jar /usr/local/share/wiki/wiki.jar $*' >> wiki
chmod u+x wiki
mv wiki /usr/local/bin