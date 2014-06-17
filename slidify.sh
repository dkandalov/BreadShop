#/bin/bash

sed 's/<p>--<\/p>/<\/section><section>/g' | sed 's/<p>+++<\/p>/<section>/g' | sed 's/<p>-+-+<\/p>/<\/section>/g'