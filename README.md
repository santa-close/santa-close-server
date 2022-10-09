# santa-close backend

![build](https://github.com/santa-close/santa-close-server/actions/workflows/gradle.yml/badge.svg)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=santa-close_santa-close-server&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=santa-close_santa-close-server)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=santa-close_santa-close-server&metric=coverage)](https://sonarcloud.io/summary/new_code?id=santa-close_santa-close-server)

지도 서비스를 기반으로 등산한 산과 식사한 식당을 등록하여 히스토리를 기록할 수 있는 앱 프로덕트입니다. 주요 기능은 아래와 같습니다.

## 가입/인증 관련

- 카카오톡으로 가입할 수 있다.
- 가입한 회원을 관리자가 인증할 수 있다.
- 산/식당의 등록은 인증된 회원만 작성할 수 있다.
- 산/식당의 후기, 이미지, 좋아요/별로에요 등록은 인증된 회원만 할 수 있다.

## 지도 관련 기능

- 지도에 좌표 기반으로 산을 등록할 수 있다.
  - 산 이름
  - 위치
  - 산에 대한 설명
  - 이미지 등록
- 지도에 좌표 기반으로 식당을 등록할 수 있다.
  - 식당 이름
  - 위치
  - 식당에 대한 설명
  - 이미지 등록
- 필터로 산 또는 식당을 필터링 할 수 있다.
  - 산 또는 식당만 표시

## 리스트 관련 기능

- 추천 순으로 정렬하여 표시할 수 있다.
- 댓글 순으로 정렬하여 표시할 수 있다.

## 산/식당 상세 관련 기능

- 산과 식당을 등록할 때, 컨텐츠를 작성할 수 있다.
  - 이름
  - 위치 선택(지도)
  - 설명
  - 이미지 등록
- 산/식당 상세를 수정할 수 있다.(작성자만)
- 산/식당 상세를 삭제할 수 있다.(작성자만)
- 상세에서 추가로 산/식당에 대한 이미지를 업로드할 수 있다.
  - 업로드 한 사진은 본인만 삭제 가능
- 상세에서 좋아요 / 별로에요(=추천) 선택할 수 있다.
  - 본인의 좋아요 / 별로에요는 본인만 취소 가능
- 후기를 작성할 수 있다.
  - 본인의 후기만 수정할 수 있다.
  - 본인의 후기만 삭제할 수 있다.

## 프로필

- 나의 프로필 사진을 업로드 할 수 있다.
  - 기본 이미지 존재
- 나의 닉네임을 설정할 수 있다.
- 나의 소개를 설정할 수 있다.
