import sys
import json
import asyncio
import aiohttp
import ssl
from urllib.parse import urljoin
import cv2
import numpy as np
import base64
import chardet
from bs4 import BeautifulSoup

# OpenCV 얼굴 인식 모델 로드
face_cascade = cv2.CascadeClassifier(cv2.data.haarcascades + "haarcascade_frontalface_default.xml")

# SSL 검증 비활성화 (임시 해결책, 보안 문제 없음)
ssl_context = ssl.create_default_context()
ssl_context.check_hostname = False
ssl_context.verify_mode = ssl.CERT_NONE

# User-Agent 설정 (Google 우회)
HEADERS = {
    "User-Agent": "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36"
}


def is_base64_encoded_data(img_url: str) -> bool:
    """Base64 인코딩된 이미지인지 확인"""
    return img_url.startswith('data:image')


def decode_base64_image(base64_data: str) -> np.ndarray:
    """Base64 인코딩된 이미지를 디코딩하여 NumPy 배열로 변환"""
    try:
        encoded_data = base64_data.split(',')[1]
        image_data = base64.b64decode(encoded_data)
        return np.frombuffer(image_data, dtype=np.uint8)
    except (IndexError, ValueError):
        return np.array([])


async def fetch_image(session, img_url: str) -> np.ndarray:
    """비동기로 이미지를 가져와 NumPy 배열로 변환"""
    try:
        async with session.get(img_url, headers=HEADERS, ssl=ssl_context, timeout=5) as response:
            if response.status != 200:
                return np.array([])

            image_data = await response.read()
            return np.asarray(bytearray(image_data), dtype=np.uint8)
    except Exception:
        return np.array([])


async def find_faces_in_image(session, img_url: str) -> bool:
    """비동기로 이미지에서 얼굴을 감지"""
    if is_base64_encoded_data(img_url):
        image_array = decode_base64_image(img_url)
    else:
        image_array = await fetch_image(session, img_url)

    if image_array.size == 0:
        return False

    image = cv2.imdecode(image_array, cv2.IMREAD_COLOR)
    if image is None:
        return False

    faces = face_cascade.detectMultiScale(image, scaleFactor=1.1, minNeighbors=5, minSize=(30, 30))
    return len(faces) > 0


async def find_faces_in_website(url: str) -> str:
    """웹사이트에서 얼굴이 포함된 이미지 URL을 JSON 형식으로 반환 (비동기)"""
    try:
        async with aiohttp.ClientSession() as session:
            async with session.get(url, headers=HEADERS, ssl=ssl_context, timeout=5) as response:
                if response.status != 200:
                    return json.dumps({"error": f"Request error: {response.status}"}, ensure_ascii=False, indent=4)

                # chardet을 사용하여 인코딩 감지
                content = await response.read()
                detected_encoding = chardet.detect(content)["encoding"]
                page_content = content.decode(detected_encoding if detected_encoding else "utf-8")

                soup = BeautifulSoup(page_content, 'html.parser')
                img_tags = soup.find_all('img')

                face_detected_urls = []

                # 비동기적으로 모든 이미지 처리
                tasks = []
                for img in img_tags:
                    img_url = img.get('src')
                    if not img_url:
                        continue

                    img_url = urljoin(url, img_url) if not img_url.startswith('http') else img_url
                    tasks.append(find_faces_in_image(session, img_url))

                # 비동기 병렬 처리 실행
                results = await asyncio.gather(*tasks)

                # 얼굴이 검출된 이미지만 추가
                for img_url, has_face in zip([img.get('src') for img in img_tags], results):
                    if has_face:
                        face_detected_urls.append(img_url)

                return json.dumps({"face_detected_images": face_detected_urls}, ensure_ascii=False, indent=4)

    except Exception as e:
        return json.dumps({"error": f"Unexpected error: {e}"}, ensure_ascii=False, indent=4)


# 실행 코드
if __name__ == "__main__":
    #site_url = sys.argv[1]
    site_url = "https://www.google.com/search?q=%EC%82%AC%EB%9E%8C&newwindow=1&sca_esv=d30c0cc97334f82b&udm=2&biw=1470&bih=798&ei=nerLZ7SrGpLm2roPvpm7-AM&ved=0ahUKEwj07cKl9PmLAxUSs1YBHb7MDj8Q4dUDCBQ&uact=5&oq=%EC%82%AC%EB%9E%8C&gs_lp=EgNpbWciBuyCrOuejDILEAAYgAQYsQMYgwEyCxAAGIAEGLEDGIMBMgsQABiABBixAxiDATILEAAYgAQYsQMYgwEyCBAAGIAEGLEDMgsQABiABBixAxiDATIFEAAYgAQyCBAAGIAEGLEDMggQABiABBixAzILEAAYgAQYsQMYgwFIkRhQuwZYohdwCngAkAECmAHVAaABuAmqAQUwLjcuMrgBA8gBAPgBAZgCDqACmweoAgDCAgYQABgHGB7CAgQQABgDwgIHEAAYgAQYCpgDAYgGAZIHBTguNC4yoAeVLA&sclient=img"
    result_json = asyncio.run(find_faces_in_website(site_url))

    # 결과 출력
    print(result_json)