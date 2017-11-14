from django.shortcuts import render
from django.http import HttpResponse, JsonResponse
from django.views.decorators.csrf import csrf_exempt
import boto3
import tempfile

bucket_name = 'papao-s3-bucket'
s3 = boto3.resource('s3')
bucket = s3.Bucket(bucket_name)

def getImage(request, key):
    import pdb;pdb.set_trace()
    f = tempfile.TemporaryFile()
    bucket.download_fileobj(f,
    return HttpResponse()

@csrf_exempt
def postImage(request):
    import pdb;pdb.set_trace()
    bucket.upload_fileobj(request.FILES['image_file'],request.POST['key'])
    return JsonResponse({'status':'OK','image_url':'localhost:8000/v1/'+request.POST['key']})

def index(request):
    return HttpResponse("Hello, world!")

