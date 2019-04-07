import json
#from botocore.vendored import requests
import boto3
from datetime import datetime
import sys
defaultencoding = 'utf-8'
if sys.getdefaultencoding() != defaultencoding:
    reload(sys)
    sys.setdefaultencoding(defaultencoding)
import re
import csv
import json
import boto3
import pandas as pd 

s3 = boto3.client("s3")
def lambda_handler(event, context):
    targetbucket = 'BUCKET NAME'
    csvkey = 'FILENAME.csv'
    pklkey = 'db.pkl'
    jsonkey = 'FILENAME.json'
    
    s3 = boto3.resource('s3')
    pkl_object = s3.Object(targetbucket, pklkey)
    pkl_content = csv_object.get()['Body'].read()
    s3_client = boto3.client('s3')
    df =pd.read_pickle(pkl_content)
    info = str(event["course"])
    def hasNumbers(inputString):
        return bool(re.search(r'\d', inputString))
    if hasNumbers(info):
        course=process.extract(info, courseList, limit=1)
        courseIndex = courseList.find(course)
        res = df.iloc[courseIndex]
        return {
        'statusCode': 200,
        'body': json.dumps(res.decode('utf8'))
    }
    else:    
        res ='no Result'
        return {
        'statusCode': 200,
        'body': json.dumps("not found")
    }
        
    
