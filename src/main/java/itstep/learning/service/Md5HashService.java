package itstep.learning.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5HashService implements HashService {
    @Override
    public String getHexHash( String str ) {
        MessageDigest md ;
        try {
            md = MessageDigest.getInstance( "MD5" ) ;
        }
        catch( NoSuchAlgorithmException ex ) {
            System.err.println( "Md5HashService: " + ex.getMessage() ) ;
            return null ;
        }
        StringBuilder hexString = new StringBuilder();
        for( byte b : md.digest( str.getBytes() ) ) {
            hexString.append( String.format( "%02x", b ) ) ;
        }
        return hexString.toString() ;
    }
}
