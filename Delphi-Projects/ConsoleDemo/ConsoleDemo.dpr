program ConsoleDemo;

{$APPTYPE CONSOLE}
{$R *.res}

uses
    System.SysUtils, SynCommons, SynCrypto;

const
    my_key = 'abc';


procedure TestAES();
var
    key: TSHA256Digest;
    aes: TAESCBC;
    s: RawByteString;
    IVStr: String;
    msg: String;
    enc_msg: String;
    dec_msg: String;
    I: Integer;
    tempIV: TAESBlock;
    sha1_digest: TSHA1Digest;
begin
    msg := 'hello world';
    IVStr := 'pemgail9uzpgzl88';
    // 1 - Encryption
    SynCommons.HexToBin(Pointer(SHA256(my_key)), @key, 32);



    s := '';
    for I := 0 to 31 do
    begin
        s := s + IntToStr(key[I]) + ' ';
    end;
    Writeln('key = ' + s);

    //
    aes := TAESCBC.Create(key, 256);
    for I := 0 to 15 do
    begin
        // S := S + intToStr(aes.IV[I]) + ' ';
        tempIV[I] := Ord(IVStr[I + 1]) - 48;
        // Write(aes.IV[I]);
    end;
    aes.IV := tempIV;

    // Writeln(S);
    // Edit2.Text := S;
    // Edit2.Text := IntToStr(aes.KeySize);            // 256

    s := StringToUTF8(msg);
    s := BinToBase64(aes.EncryptPKCS7(s, False)); // True
    enc_msg := UTF8ToString(s);
    Writeln('encrypted msg = ' + enc_msg);
    //
    s := '';
    for I := 0 to 15 do
    begin
        s := s + Chr(aes.IV[I] + 48) + ' ';
    end;
    Writeln('aes.IV = ' + s);


    // 2 - Decryption
    s := StringToUTF8(enc_msg);
    s := aes.DecryptPKCS7(Base64ToBin(s), False);   // True
    dec_msg := UTF8ToString(s);

    Writeln('decrypted msg = ' + dec_msg);

    aes.Free;
    //
    Readln;
end;

procedure TestHash();
var
    hkey: String;
    key: TSHA256Digest;
    sha1_digest: TSHA1Digest;
    s: String;
    I: Integer;
begin
    hkey := '';
    HMAC_SHA1(StringToUTF8(hkey), my_key, sha1_digest);

//    s := '';
//    for I := 0 to 19 do
//    begin
//        s := s + IntToStr(key[I]) + ' ';
//    end;
//    Writeln('key = ' + s);

    s := '';
    for I := 0 to 19 do
    begin
        s := s + IntToStr(sha1_digest[I]) + ' ';
    end;
    Writeln('sha1_digest = ' + s);

//    SynCommons.HexToBin(sha1_digest, @key, 32);
//    s := '';
//    for I := 0 to 19 do
//    begin
//        s := s + IntToStr(key[I]) + ' ';
//    end;
//    Writeln('key = ' + s);

    Readln;
end;

////////////////// MAIN
begin

//    TestAES();

    TestHash();

end.
