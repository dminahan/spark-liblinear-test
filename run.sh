#!/user/bin/env bash

#------------------------------
# template variables [${profie.current.name}]
#------------------------------

JOB_NAME="${project.build.finalName}"
JAR_NAME="${shaded.jar.name}"
JAR_MAIN_CLASS="${shaded.main.class}"

#---------------------
# other variables
#--------------------

#spark-related
MASTER="yarn"
MODE="client"
LOG_OPTION="-Dlog4j.configuration='file://${project.build.directory}/log4j.properties'"

#-----------------------
# run the job
#-----------------------

echo "$NOW <$0> Starting job [$JOB_NAME]...."

#Yarn submission
set -x
spark-submit \
     --verbose \
     --class $JAR_MAIN_CLASS \
     --master $MASTER \
     --name $JOB_NAME \
     --driver-memory 4g \
     --executor-memory 2g \
     --num-executors 4 \
     --executor-cores 4 \
     --deploy-mode $MODE \
     --conf "spark.driver.extraJavaOptions=${LOG_OPTION}" \
     $JAR_NAME
set +x
