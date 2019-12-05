$htmlFile = [System.IO.File]::OpenText("C:\Source\cs400\hrtoons\src\view-source_hrwiki.org_wiki_All_Toons.txt")
while(-not $htmlFile.IsEndOfStream)
{
    $csvLine = ""
    # Release Date, Day of Week, Type, Title, HR Wiki Link, Watch Link, Easter Eggs, Run Time
    $htmlLine = $htmlFile.nextLine()
    if ($htmlLine.contains("display:none"))
    {
        $htmlLine = $htmlLine.substring($htmlLine.indexOf("display:none"))

        for($i = 0; $i < 9; i++)
        {
            $htmlLine = $htmlLine.substring($htmlLine.indexOf("&gt;") + 3)

            $startIndex = $htmlLine.indexOf("</span>") + 7
            $toAdd = $htmlLine.substring($startIndex,$htmlLine.indexOf("<span class") - $startIndex)

            if(-not [string]::IsNullOrWhiteSpace($toAdd))
            {
                $csvLine += $toAdd + ","
            }
        }

        Add-Content -Path "C:\Source\cs400\hrtoons\src\hrtoonsfull.csv" -Value $csvLine

    }
}