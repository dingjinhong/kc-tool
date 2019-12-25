#!/usr/bin/python
# -*- coding: UTF-8 -*-

import requests
import re
from bs4 import BeautifulSoup
import sys
import os
import time
from selenium import webdriver
from selenium.webdriver.chrome.options import Options

#print(sys.argv[2])
chromedriver = sys.argv[2]+'\python\driver\win32\chromedriver.exe'
os.environ['webdriver.chrome.driver'] = chromedriver
def get_html(url):
    headers = {
        'User-Agent':'Mozilla/5.0(Macintosh; Intel Mac OS X 10_11_4)\
        AppleWebKit/537.36(KHTML, like Gecko) Chrome/52 .0.2743. 116 Safari/537.36'
 
    }    
    response = requests.get(url,headers = headers)      
    response.encoding='GBK'
    html = response.text       
    #print(html)
    return html                

def get_source_html(url,driver_path):
	opts = Options()
	opts.headless = True
	#opts.add_argument('--headless')
	#chrome_options.add_argument('--headless')
	
	try:
		
		browser = webdriver.Chrome(options=opts,executable_path=driver_path)
	    #browser = webdriver.Firefox() # Get local session of firefox
		browser.get(url) # Load page
		html = browser.page_source
		#print(html)
		return html
	except BaseException as e:
		print(e);
		return ''

if __name__ == "__main__":
	url=sys.argv[1];
	soup = BeautifulSoup(get_source_html(url,chromedriver), 'lxml')   
	if url.find('wenda.so')!=-1:
		for span in soup.select('span[class="mr-prof"]'):
			if span.text.find('浏览')!=-1:
				print(span.text)
	elif url.find('zhidao.baidu')!=-1:
		for span in soup.find_all(name='span'):
			if span.text.find('浏览')!=-1:
			#print("1111");
			    print(span.text)
	elif url.find('wenwen.sogou')!=-1:
		for span in soup.find_all(name='span'):
			if span.text.find('浏览')!=-1:
			#print("1111");
			    print(span.text)
	elif url.find('zhihu')!=-1:
		for span in soup.select('div[class="NumberBoard-itemInner"]'):
			if span.text.find('浏览')!=-1:
				print(span.text)

