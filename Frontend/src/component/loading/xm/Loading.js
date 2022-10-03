import React from "react";
import Lottie from "lottie-react";
import loading from "./loading.json";

import './Loading.css'

const style = {
    // height: '40px',
    // width: '200px',
    scale: '20%'
};

const Loading = () => {
    return (
        <Lottie animationData={loading} loop={true} style={style} />
    )
} 

export default Loading;