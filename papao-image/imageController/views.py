from django.shortcuts import render
from django.http import HttpResponse, JsonResponse
from django.views.decorators.csrf import csrf_exempt
import boto3
import tempfile

bucket_name = 'papao-s3-bucket'
s3 = boto3.resource('s3')
bucket = s3.Bucket(bucket_name)
hostname = "localhost:8000/v1/"


def getImage(request, key):
    f = tempfile.TemporaryFile()
    bucket.download_fileobj(f)
    return HttpResponse()


@csrf_exempt
def postImage(request):
    files = request.FILES.getlist('file')
    filenames = list(map(lambda x: uploadImage(x), files))
    return JsonResponse({'status': 'OK', 'image_url': list(map(lambda x:hostname+x,filenames))})


def index(request):
    return HttpResponse("Hello, world!")


def uploadImage(file):
    bucket.upload_fileobj(file, file.name)
    return file.name
