import React, { useEffect, useRef, useState } from 'react'
import './SelectDeliveryCompany.scss'
import data from './data'
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";
import Slider from 'react-slick';

const Cards = (props) => {
  return (
    <div className="delivery-company-container">
      <div className='card-list'>
        {
          props.items.map((item, index) => (
            <div className='card-item' key={index}>
              <div className='delivery-ci-container'>
                <img src={item.src} alt={item.name} />
              </div>
              <div className='card-name'>
                {item.name}
              </div>
            </div>
          ))
        }
      </div>
    </div>
  )
}

function SelectDeliveryCompany(props) {
  const [display, setDisplay] = useState(false);
  const [slideIndex, setSlideIndex] = useState(0);
  const sliderRef = useRef();

  const slickSetting = {
    infinite: true,
    speed: 500,
    slidesToShow: 1,
    slidesToScroll: 1,
    beforeChange: (current, next) => setSlideIndex(next)
  }

  useEffect(() => {
    setDisplay(props.display);
  })

  const closeModal = () => {
    setDisplay(!display)
  }

  const moveTab = index => {
    sliderRef.current.slickGoTo(index);
  }


  return (
    <div className={'drawer-root-container' + (display ? ' show' : ' hide')}>
      <div className='wrapper'>
        <div className='drawer-title'>
          택배사 선택
        </div>
        <div className='button close'>
          <img src='/asset/close_modal.svg' alt='close' onClick={closeModal}></img>
        </div>

        <div className='delivery-division-box button'>
          <div className={'delivery-division-tab' + (slideIndex === 0 ? ' active': '')} onClick={() => moveTab(0)}>
            국내택배
          </div>
          <div className={'delivery-division-tab' + (slideIndex === 1 ? ' active': '')} onClick={() => moveTab(1)}>
            쇼핑몰택배
          </div>
          <div className={'delivery-division-tab' + (slideIndex === 2 ? ' active': '')} onClick={() => moveTab(2)}>
            해외택배
          </div>
        </div>

        <Slider {...slickSetting} ref={sliderRef}>
          <Cards items={data.filter(v => v.division === 1)}/>
          <Cards items={data.filter(v => v.division === 2)}/>
          <Cards items={data.filter(v => v.division === 3)}/>
        </Slider>


        <div className='button button-container'>
          <div className='button-name'>
            배송조회
          </div>
        </div>
      </div>
    </div>
  );
}

export default SelectDeliveryCompany;
