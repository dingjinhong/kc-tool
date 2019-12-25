#!/usr/bin/python
# -*- coding: UTF-8 -*-

import urllib.request
import re
import os
import urllib
import sys

def get_image(dirName,imageUrl,imageName):
	#dirName = os.path.abspath(".") + "\\image"     # 文件下载在项目路径下
	if not os.path.isdir(dirName):
		os.makedirs(dirName)  # 判断没有此路径则创建
	#i_headers = {
	#	"User-Agent": "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.104 Safari/537.36 Core/1.53.4882.400 QQBrowser/9.7.13059.400",
	#	"Referer": "http://699pic.com/?sem=1&sem_kid=33723&sem_type=1"
	#}
	fileName = dirName + "\\"    # 这里名字先随便起了
	urllib.request.urlretrieve(imageUrl, '{0}{1}.png'.format(fileName,imageName)) 


if __name__ == "__main__":
	imageUrl=sys.argv[1]
	dirName=sys.argv[2]
	imageName=sys.argv[3]
	print(imageName)
	get_image(dirName,imageUrl,imageName)