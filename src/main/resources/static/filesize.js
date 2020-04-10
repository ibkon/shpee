function fileAutoSize(size) {
    if(size<0x400)
        return size+'B';
    else if(size<0x100000)
        return (size/1024).toFixed(2)+'KB';
    else if(size<40000000)
        return (size/0x100000).toFixed(2)+'MB';
    else
        return (size/0x40000000).toFixed(2)+'GB';
}