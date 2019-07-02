#한글 미출력 문제 해결
import sys
import io
sys.stdout = io.TextIOWrapper(sys.stdout.detach(), encoding='utf-8')
sys.stderr = io.TextIOWrapper(sys.stderr.detach(), encoding='utf-8')

# 네이버 카페 DeepOMS 글쓰기 예제
import os
import threading
import time
import requests
import configparser
from bs4 import BeautifulSoup
from selenium import webdriver
from random import randrange
from selenium.webdriver.common.keys import Keys


def naver_blog_write():

    boardcontents = 'contents'

    # Selenium을 통한 네이버 로그인
    driver = webdriver.Chrome('C:/Program Files/chromedriver')
    driver.get('https://finance.naver.com/')
    driver.implicitly_wait(3)
    time.sleep(2)
    driver.find_element_by_xpath('//*[@id="gnb_login_button"]/span[3]').click()
    time.sleep(randrange(2,4))

    # 설정파일 연동(.properties)
    config = configparser.ConfigParser()
    current_dir = os.path.abspath(os.path.dirname(__file__))
    parent_dir = os.path.abspath(current_dir + "/../")
    target_dir = parent_dir + "\\config\\SNSConfig.ini"
    print(target_dir)

    config.read(target_dir, encoding='UTF-8')
    id = config['DeepBid']['ID']
    pw = config['DeepBid']['PW']

    driver.execute_script("document.getElementsByName('id')[0].value=\'" + id + "\'")
    time.sleep(randrange(2,4))
    driver.execute_script("document.getElementsByName('pw')[0].value=\'" + pw + "\'")
    driver.find_element_by_xpath('//*[@id="frmNIDLogin"]/fieldset/input').click()

    # DeepOMS CAFE 접속 후 글쓰기
    time.sleep(randrange(2,4))
    driver.get('https://blog.naver.com/ga_blog')
    driver.switch_to_frame("mainFrame")
    driver.find_element_by_xpath('//*[@id="post-admin"]/a[1]').click()

    # 네이버 실시간 검색어 추출
    html = requests.get('https://naver.com/').text
    soup = BeautifulSoup(html, 'html.parser')

    boardtitle = config['DeepBid']['TITLE']
    realtime_search_word = soup.select('#PM_ID_ct > div.header > div.section_navbar > div.area_hotkeyword.PM_CL_realtimeKeyword_base > div.ah_list.PM_CL_realtimeKeyword_list_base > ul > li > a.ah_a > span.ah_k')
    for index, title in enumerate(realtime_search_word, 1):
        if index == 1:
            boardcontents = str(index) + "위 " + title.text + config['DeepBid']['CONTENTS']

    driver.execute_script('document.getElementById("subject").value="' + boardtitle + '"')
    driver.switch_to_frame("se2_iframe")
    driver.find_element_by_xpath('/html/body').send_keys(boardcontents)
    driver.switch_to_default_content()
    driver.switch_to_frame("mainFrame")
    driver.find_element_by_css_selector('#btn_preview').click() #버튼 클릭이 되지 않을 때 사용
    time.sleep(randrange(2,4))
    driver.close()

    #스레드 주기 재귀실행 cycle 단위 : sec
    cycle = config['DeepBid']['CYCLE']
    threading.Timer(cycle, naver_blog_write).start()

naver_blog_write()
